package initializers

import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import services.TaskServiceImpl

interface Initializer {
    fun init()
}

fun Application.initJobs(){
    val taskServiceImpl: TaskServiceImpl by inject()
    val initJobs: List<Initializer> = listOf(TaskInitializer(taskServiceImpl))
    initJobs.forEach {it.init()}
}