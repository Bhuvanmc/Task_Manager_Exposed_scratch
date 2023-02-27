package initializers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dto.TaskInfo
import models.Task
import mu.KotlinLogging
import services.TaskServiceImpl
import java.io.File

class TaskInitializer(
    private val taskServiceImpl: TaskServiceImpl
): Initializer {

    private val logger = KotlinLogging.logger {}

    override fun init() {
        val taskFile = File(this::class.java.getResource("/data/tasks.json").toURI())
        val taskFileContent = taskFile.readText(Charsets.UTF_8)
        var taskInfoList = jacksonObjectMapper().readValue(taskFileContent, List::class.java) as List<Task>
        taskInfoList = jacksonObjectMapper().convertValue(taskInfoList, object : TypeReference<List<Task>>() {})
        taskInfoList.forEach() {it ->
            taskServiceImpl.createOrUpdate(it)}
    }

}