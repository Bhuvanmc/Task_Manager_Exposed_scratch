package services

import dto.TaskInfo
import models.Task

interface TaskService {
    fun allTasks(): List<Task>
    fun task(id:String): Task?
    fun addTask(taskInfo: TaskInfo): Task?
    fun updateTask(id:String, taskInfo: TaskInfo):Boolean
    fun deleteTask(id:String):Boolean
}