package routes

import dto.TaskInfo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject
import services.taskServiceImpl

//import services.dao.dao

fun Route.taskRouting(){
    val dao : taskServiceImpl by inject()
    route("/"){
        get{
            call.respondText("Welcome to Task Manager")
        }
    }
    route("/tasks"){
        get{
            call.respond(dao.allTasks())
//            call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to dao.allTasks())))
        }
        get ("/{id}"){
            val id = call.parameters["id"]?: return@get call.respondText("Missing ID",status= HttpStatusCode.BadRequest)
            call.respond(dao.task(id))
        }
        post {
            val taskInfo = call.receive<TaskInfo>()
            val task = dao.addTask(taskInfo)
//            println(task?.id?:"No id returned")
            call.respondRedirect("/tasks/${task?.id}")
        }

        post("/{id}/update") {
            val id = call.parameters["id"]?: return@post call.respondText("Missing ID",status= HttpStatusCode.BadRequest)
            val taskInfo = call.receive<TaskInfo>()
            val isSuccess = dao.updateTask(id, taskInfo)
            call.respondRedirect("/tasks")
            }

        delete ("{id?}"){
            val id = call.parameters["id"]?: return@delete call.respondText("Missing ID",status= HttpStatusCode.BadRequest)
            if(dao.deleteTask(id))
                call.respondText("Task $id deleted successfully", status = HttpStatusCode.Accepted)
            else
                call.respondText("Error deleting Task $id", status = HttpStatusCode.BadRequest)
        }
    }
}