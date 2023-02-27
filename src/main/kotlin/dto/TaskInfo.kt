package dto



//import kotlinx.serialization.Serializable

//import io.ktor.serialization.jackson.*
import models.Task
import org.jetbrains.exposed.sql.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//@Serializable
data class TaskInfo(val name:String,
                    val description:String,
                    val created_by:String,
                    val assignee:String,
                    val status:String,
                    val severity:String){
    fun toTask(id: String): Task{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val currentTime = LocalDateTime.now().format(formatter)
        return Task(
            id,
            name,
            description,
            created_by,
            currentTime,
            currentTime,
            assignee,
            status,
            severity
        )
    }
}
