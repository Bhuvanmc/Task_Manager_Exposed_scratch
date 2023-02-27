package models.repositories

import appconfig.AppConfig
import dto.TaskInfo
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import models.Task
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock
import java.sql.Connection
import javax.sql.DataSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Testing task repository")
class TaskRepositoryScope {

    private lateinit var epg: EmbeddedPostgres
    private lateinit var database: Database
    private lateinit var taskRepository: TaskRepository

    fun initDb(conn: Connection){
        conn.use { conn ->
            conn.prepareStatement("CREATE DATABASE ${AppConfig.ALERTS_SCHEMA_NAME}").execute()
            conn.prepareStatement("CREATE SCHEMA ${AppConfig.ALERTS_SCHEMA_NAME}").execute()
            val database: liquibase.database.Database =
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(conn))
            database.defaultSchemaName = AppConfig.ALERTS_SCHEMA_NAME
            val liquibase = Liquibase("db/liquibase-changelog-master.xml", ClassLoaderResourceAccessor(), database)
            liquibase.update(Contexts())
        }
    }
    val task1 = Task("AOC_1",
        "AOC-7784",
        "desc",
        "Akshay",
        "",
        "",
        "Bhuvan",
        "completed",
        "l2"
    )

    @BeforeAll
    fun beforeAll(){
//        mockDb = mock(Database::class.java)
//        epg = EmbeddedPostgres.start()
        epg = EmbeddedPostgres.builder().start()
//            .setPgDirectoryResolver(
//                dir -> new File("/tmp/postgresql-12.9-1-osx-binaries/pgsql/"))
//        .start()
        val dataSource = epg.postgresDatabase
        val conn = dataSource.connection
        database = Database.connect(dataSource!!)
        initDb(conn)
        taskRepository = TaskRepository(database)
    }

    @Test
    @Order(1)
    @DisplayName("Testing create task")
    fun createTest() {
        val task = taskRepository.create(task1)
        assertEquals("AOC_1", task!!.id)
    }

    @Test
    @Order(2)
    @DisplayName("Testing Findall")
    fun findAllTest() {
        assertEquals(1, taskRepository.findAll().size)
    }

    @Test
    @Order(3)
    @DisplayName("Testing find by id")
    fun findByIdTest() {
        assertEquals("AOC_1", taskRepository.findById("AOC_1")?.id)
    }

    @Test
    @Order(4)
    @DisplayName("Testing find by id when the task is not present. Should return null")
    fun findByIdTestNull() {
        val task = taskRepository.findById("AOC_5")
        assertEquals(null, task)
    }

    @Test
    @Order(5)
    @DisplayName("Testing new task by id")
    fun findByIdTest2() {
        assertEquals("AOC_1", taskRepository.findById("AOC_1")?.id)
    }

    @Test
    @Order(6)
    @DisplayName("Testing get next id after creating task with id AOC_1")
    fun getNextIdTest() {
        val nextId = taskRepository.getNextId()
        assertEquals("AOC_2", nextId)
    }

    @Test
    @DisplayName("Testing task updation")
    fun updateTest() {
    }

    @Test
    @DisplayName("Testing task deletion")
    fun deleteTest() {
    }
}