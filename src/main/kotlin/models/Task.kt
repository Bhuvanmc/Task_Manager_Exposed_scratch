package models



//import kotlinx.serialization.Serializable

//import io.ktor.serialization.jackson.*
import org.jetbrains.exposed.sql.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//@Serializable
//@Serializable
data class Task(var id:String,
                var name:String,
                var description:String,
                var created_by:String,
                var created_time:String,
                var updated_time:String,
                var assignee:String,
                var status:String,
                var severity:String)
