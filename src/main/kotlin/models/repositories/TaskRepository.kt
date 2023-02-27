package models.repositories

import dto.TaskInfo
import models.Task
import models.repositories.TaskRepository.Companion.toTask
import models.repositories.tables.Tasks
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskRepository(private val db: Database) {
    fun findAll(): List<Task>{
        return transaction(db) {
            Tasks.selectAll().map{ it.toTask() }
        }
    }

    fun findById(id: String): Task?{
        return transaction(db){
            Tasks.select{Tasks.id eq id}.map { it.toTask() }.firstOrNull()
        }
    }

    fun create(task: Task): Task?{
        return transaction(db) {
            val insertStatement = Tasks.insert {
                it[id] = task.id
                it[name] = task.name
                it[description] = task.description
                it[created_by] = task.created_by
                it[created_time] = task.created_time
                it[updated_time] = task.updated_time
                it[assignee] = task.assignee
                it[status] = task.status
                it[severity] = task.severity
            }
            val temp = insertStatement.resultedValues?.firstOrNull()?.toTask()
            temp
        }
    }

    fun getNextId(): String{
        return transaction(db) {
            val count = Tasks.selectAll().count()
            val lastId = Tasks.slice(Tasks.id).selectAll().map {
                it[Tasks.id].substring(4).toInt()
            }.maxOrNull()?:0
            "AOC_${lastId+1}"

//            val sliceQuery = Tasks.slice(Tasks.id)
//            val selectQuery = sliceQuery.selectAll()
//            val taskList = selectQuery.map{ it[Tasks.id].substring(4).toInt()}
//            val lastId = taskList.maxOrNull()?:0
//            "AOC_${lastId+1}"
        }
    }
    fun update(task: Task): Boolean {
        return transaction(db) {
            Tasks.update({ Tasks.id eq task.id }) {
                it[Tasks.name] = task.name
                it[Tasks.description] = task.description
                it[Tasks.created_by] = task.created_by
                it[Tasks.updated_time] = task.updated_time
                it[Tasks.assignee] = task.assignee
                it[Tasks.status] = task.status
                it[Tasks.severity] = task.severity
            } > 0
        }
    }

    fun delete(id: String): Boolean{
        return transaction(db){
            var stat = false
            Tasks.deleteWhere {Tasks.id eq id}.let { if (it > 0) stat=true }
            stat
        }
    }

    companion object {
        private fun ResultRow.toTask() = Task(
            this[Tasks.id],
            this[Tasks.name],
            this[Tasks.description],
            this[Tasks.created_by],
            this[Tasks.created_time],
            this[Tasks.updated_time],
            this[Tasks.assignee],
            this[Tasks.status],
            this[Tasks.severity]
        )
    }
}