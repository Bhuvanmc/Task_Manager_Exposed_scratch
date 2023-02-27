import appconfig.AppConfig
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import initializers.initJobs
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import models.repositories.TaskRepository
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import routes.*
import services.TaskServiceImpl

fun appModule(config: ApplicationConfig) = module{
    single<Config> { ConfigFactory.load() }
    single { AppConfig(config) }
    single {
        val appConfig:AppConfig by inject()
        val dataSource = appConfig.dbConfig.createDataSource()
//        Database.connect(url = dataSource.jdbcUrl, user = dataSource.username, password = dataSource.password)
        Database.connect(datasource = dataSource)
    }
    single{
        TaskRepository(get())
    }
    single {
        TaskServiceImpl(get())
    }
}
fun Application.module(){
    install(Koin) {
        modules(appModule(environment.config))
    }
//    Below way of starting koin doesn't support autoreloading
//    startKoin{modules(appModule(environment.config))}
//    DatabaseFactory.init(environment.config)
//    val database:Database by inject()
    initDb()
    initJobs()
    configureSerialization()
    configureRouting()
}

fun Application.configureSerialization() {
    install(ContentNegotiation){
        jackson {  }
    }
}
fun Application.configureRouting() {
    val dao : TaskServiceImpl by inject()
    routing {
        taskRouting()
    }
}


