package dto



//import kotlinx.serialization.Serializable

//import io.ktor.serialization.jackson.*
import org.jetbrains.exposed.sql.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//@Serializable
data class TaskInfo(var name:String,
                    var description:String,
                    var created_by:String,
                    var assignee:String,
                    var status:String,
                    var severity:String) {

}
