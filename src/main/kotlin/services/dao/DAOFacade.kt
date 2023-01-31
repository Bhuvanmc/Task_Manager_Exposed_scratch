package services.dao

import models.*

interface DAOFacade {
    suspend fun allTasks(): List<Task>
    suspend fun task(id:String):Task
    suspend fun addTask(taskInfo:TaskInfo):Task?
    suspend fun updateTask(id:String, taskInfo: TaskInfo):Boolean
    suspend fun deleteTask(id:String):Boolean
}