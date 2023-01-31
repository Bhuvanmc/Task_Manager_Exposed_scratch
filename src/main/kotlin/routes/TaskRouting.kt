package routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.TaskInfo
import services.dao.DAOFacadeImpl
import services.dao.DatabaseFactory
//import services.dao.dao

fun Route.taskRouting(dao:DAOFacadeImpl){
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
        get ("{id?}"){
            val id = call.parameters["id"]?: return@get call.respondText("Missing ID",status= HttpStatusCode.BadRequest)
            call.respond(dao.task(id))
        }
        post {
            val taskInfo = call.receive<TaskInfo>()
            val task = dao.addTask(taskInfo)
            println(task?.id?:"No id returned")
            call.respondRedirect("/tasks/${task?.id}")
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