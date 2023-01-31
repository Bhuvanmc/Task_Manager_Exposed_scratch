import appconfig.AppConfig
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import routes.*
import services.dao.DAOFacade
import services.dao.DAOFacadeImpl
import services.dao.DatabaseFactory

fun appModule(config: ApplicationConfig) = module{
    single<Config> { ConfigFactory.load() }
    single { AppConfig(config) }
    single {
        val appConfig:AppConfig by inject()
        val dataSource = appConfig.dbConfig.createDataSource()
        Database.connect(url = dataSource.jdbcUrl, user = dataSource.username, password = dataSource.password)
    }
    single {
        DAOFacadeImpl()
    }
    single{}
}
fun Application.module(){
    install(Koin) {
        modules(appModule(environment.config))
    }
//    Below way of starting koin doesn't support autoreloading
//    startKoin{modules(appModule(environment.config))}
//    DatabaseFactory.init(environment.config)
    val database:Database by inject()
    DatabaseFactory.init(database)
    configureSerialization()
    configureRouting()


}

fun Application.configureSerialization() {
    install(ContentNegotiation){
        jackson {  }
    }
}
fun Application.configureRouting() {
    val dao : DAOFacadeImpl by inject()
    routing {
        taskRouting(dao)
    }
}


