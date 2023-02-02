package services

import dto.TaskInfo
import kotlinx.coroutines.Dispatchers
import models.Task
import models.repositories.tables.Tasks
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class taskServiceImpl : taskService {
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
    private fun resultRowToTask(row: ResultRow) = Task(
        row[Tasks.id],
        row[Tasks.name],
        row[Tasks.description],
        row[Tasks.created_by],
        row[Tasks.created_time],
        row[Tasks.updated_time],
        row[Tasks.assignee],
        row[Tasks.status],
        row[Tasks.severity]
    )


    override suspend fun allTasks(): List<Task> = dbQuery{
                                                    Tasks.selectAll().map{resultRowToTask(it)}
                                                    }

    override suspend fun task(id: String): Task = dbQuery {
        Tasks.select { Tasks.id eq id }.map(::resultRowToTask).single()
//        Tasks.select { Tasks.id eq id }.map(::resultRowToTask).singleOrNull()

    }

    override suspend fun addTask(taskInfo: TaskInfo): Task? =
        dbQuery {
            val count = Tasks.selectAll().count()
//            var lastIdStr:String="AOC_0"
//            val lastIdStatement = Tasks.selectAll().orderBy(Tasks.id to SortOrder.DESC).limit(1)
//            Fetch id column from table take integer part as substring, convert it to Int and select max from it, increment and use for nextid
            val lastId = Tasks.slice(Tasks.id).selectAll().map {
                it[Tasks.id].substring(4).toInt()
            }.max()

            println("\n\n\nlast id is $lastId\n\n\n")
//            val lucasDirectedQuery = Tasks.slice(Tasks.name).select { Tasks.id eq "George Lucas" }

//            val nextIdCount=lastId
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val currentTime= LocalDateTime.now().format(formatter)
            val insertStatement = Tasks.insert {
                it[Tasks.id] = "AOC_${lastId+1}"
                it[Tasks.name] = taskInfo.name
                it[Tasks.description] = taskInfo.description
                it[Tasks.created_by] = taskInfo.created_by
                it[Tasks.created_time] = currentTime
                it[Tasks.updated_time] = currentTime
                it[Tasks.assignee] = taskInfo.assignee
                it[Tasks.status] = taskInfo.status
                it[Tasks.severity] = taskInfo.severity
            }
            val temp = insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTask )
            return@dbQuery temp
        }


    override suspend fun updateTask(id: String, taskInfo: TaskInfo): Boolean = dbQuery {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val currentTime= LocalDateTime.now().format(formatter)
        Tasks.update({ Tasks.id eq id }) {
            it[Tasks.name] = taskInfo.name
            it[Tasks.description] = taskInfo.description
            it[Tasks.created_by] = taskInfo.created_by
            it[Tasks.updated_time] = currentTime
            it[Tasks.assignee] = taskInfo.assignee
            it[Tasks.status] = taskInfo.status
            it[Tasks.severity] = taskInfo.severity
        } > 0
    }

    override suspend fun deleteTask(id: String): Boolean {
        var stat=false
        dbQuery {
            Tasks.deleteWhere {Tasks.id eq id}.let { if(it > 0) stat=true }
        }
        return stat
    }
}

//val dao:DAOFacade=DAOFacadeImpl()