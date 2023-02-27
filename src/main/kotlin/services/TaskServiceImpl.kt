package services

import dto.TaskInfo
import kotlinx.coroutines.Dispatchers
import models.Task
import models.repositories.TaskRepository
import models.repositories.tables.Tasks
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskServiceImpl(
    private val taskRepository: TaskRepository
) : TaskService {

//    private suspend fun <T> dbQuery(block: suspend () -> T): T =
//        newSuspendedTransaction(Dispatchers.IO) { block() }

//    private fun resultRowToTask(row: ResultRow) = Task(
//        row[Tasks.id],
//        row[Tasks.name],
//        row[Tasks.description],
//        row[Tasks.created_by],
//        row[Tasks.created_time],
//        row[Tasks.updated_time],
//        row[Tasks.assignee],
//        row[Tasks.status],
//        row[Tasks.severity]
//    )

    override fun allTasks(): List<Task>  {
        return taskRepository.findAll()
    }

    override fun task(id: String): Task? {
        return taskRepository.findById(id)
//        Tasks.select { Tasks.id eq id }.map(::resultRowToTask).singleOrNull()
    }

    override fun addTask(taskInfo: TaskInfo): Task? {
            val task = taskInfo.toTask(taskRepository.getNextId())
            return taskRepository.create(task)
        }

    fun addTask(task: Task): Task? {
        return taskRepository.create(task)
    }

    override fun updateTask(id: String, taskInfo: TaskInfo): Boolean {
        val task = taskInfo.toTask(id)
        return taskRepository.update(task)
    }

    override fun deleteTask(id: String): Boolean {
        return taskRepository.delete(id)
    }

    fun createOrUpdate(task: Task): Task? {
        val entity0 = taskRepository.findById(task.id)
        if (entity0 != null)
            taskRepository.update(task)
        else
            taskRepository.create(task)
        return taskRepository.findById(task.id)
    }

    fun addNumbers(a: Int, b: Int): Int{
        return a+b
    }
}

//val dao:DAOFacade=DAOFacadeImpl()