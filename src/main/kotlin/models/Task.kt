package models



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

class TaskManager {
    val taskList: MutableList<Task> = mutableListOf()
    var count=0;
    val id_init="AOC"

    fun createTask( name:String,
                    description:String,
                    created_by:String,
                    assignee:String,
                    status:String,
                    severity:String):String
    {
        count++
        val id="${id_init}_$count"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        taskList.add(Task(id, name, description, created_by, current, current, assignee, status, severity))
        return id
    }
    fun printTasks()
    {
        taskList.forEach() { task->
            println("Task id:${task.id} \n" +
                    "\t name:${task.name} \n " +
                    "\t description:${task.description},\n" +
                    "\t created_by:${task.created_by},\n" +
                    "\t created_time:${task.created_time},\n" +
                    "\t updated_time:${task.updated_time},\n" +
                    "\t assignee:${task.assignee},\n" +
                    "\t status:${task.status},\n" +
                    "\t severity:${task.severity}")

        }
    }
    fun deleteTask(_id:String):Boolean{
        var index:Int=0;
        var temp:Task
        for (task in taskList){
            if(task.id==_id){
                temp=task
                break
            }
            index++
        }
        if(index>=count)
            return false
        taskList.removeAt(index)
        count--
        return true
    }

    fun updateTask(_id:String, assignee: String):Boolean{
        var found:Boolean=false
        for(task in taskList) {
            if(task.id==_id){
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val current = LocalDateTime.now().format(formatter)
                task.updated_time=current
                task.assignee=assignee
                found=true
                break
            }
        }
        if(!found)
            return false
        return true
    }
}

//var taskManager=TaskManager()

