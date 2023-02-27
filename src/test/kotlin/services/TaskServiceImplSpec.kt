package services

import dto.TaskInfo
import models.Task
import models.repositories.TaskRepository
import models.repositories.tables.Tasks
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Task Service Spec")
class TaskServiceImplSpec {

    lateinit var mockTask: Task
    lateinit var mockTasks: Tasks
    private lateinit var mockTaskInfo: TaskInfo
    private lateinit var taskServiceImpl: TaskServiceImpl
    private lateinit var returnTask:Task
    private lateinit var mockTaskRepository: TaskRepository
    @BeforeAll
    fun beforeAll(){
        mockTaskRepository = mock(TaskRepository::class.java)
        taskServiceImpl = TaskServiceImpl(mockTaskRepository)
        mockTaskInfo = TaskInfo("AOC-7784","desc","Akshay","Bhuvan","completed","l2")
    }

    @Test
    @DisplayName("Testing dbQuery coroutine")
    fun allTasksTest() {
    }

    @Test
    @DisplayName("Testing Add function")
    fun addNumbersTest(){
        assertEquals(5, taskServiceImpl?.addNumbers(3, 2))
    }


    @Test
    @DisplayName("Testing nextId function")
    fun findNextIdTest(){
        val id = mockTaskRepository?.getNextId()
        assertEquals("AOC_1", id)
    }

    @Test
    @DisplayName("Testing Task addition")
    fun addTaskTest() {
        returnTask = Task("AOC_2","AOC-7784","desc","Akshay","currentTime", "currentTime", "Bhuvan","completed","l2")
        assertEquals(1, taskServiceImpl.addTask(mockTaskInfo))
    }

    @Test
    fun updateTaskTest() {
    }

    @Test
    fun deleteTaskTest() {

    }
}