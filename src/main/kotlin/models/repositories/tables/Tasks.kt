package models.repositories.tables



//import kotlinx.serialization.Serializable

//import io.ktor.serialization.jackson.*
import org.jetbrains.exposed.sql.Table


//@Serializable

object Tasks:Table(){
    val id = varchar("id", 128)
    val name = varchar("uname", 128)
    val description = varchar("description", 1024)
    val created_by = varchar("created_by", 128)
    val created_time = varchar("created_time", 128)
    val updated_time = varchar("updated_time", 128)
    val assignee = varchar("assignee", 128)
    val status = varchar("status", 128)
    val severity = varchar("severity", 128)

    override val primaryKey = PrimaryKey(id)
}
