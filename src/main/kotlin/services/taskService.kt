package services

import dto.TaskInfo
import models.*
import models.Task

interface taskService {
    suspend fun allTasks(): List<Task>
    suspend fun task(id:String): Task
    suspend fun addTask(taskInfo: TaskInfo): Task?
    suspend fun updateTask(id:String, taskInfo: TaskInfo):Boolean
    suspend fun deleteTask(id:String):Boolean
}