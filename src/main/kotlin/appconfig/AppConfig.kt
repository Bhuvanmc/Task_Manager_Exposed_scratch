package appconfig

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.koin.core.component.KoinComponent

class AppConfig(val config: ApplicationConfig):KoinComponent {

    val dbConfig  = getDbsConfig()
    private fun getDbsConfig():DbConfig{
        return DbConfig(
            jdbcURL = config.property("storage.jdbcURL").getString(),
            userName = config.property("storage.username").getString(),
            pswd=config.property("storage.pswd").getString(),
            maxConnPoolSize=config.property("storage.maxConnPoolSize").getString().toInt()
            )
    }

    companion object {
        const val ALERTS_SCHEMA_NAME = "tm_tasks"
    }
}

data class DbConfig(val jdbcURL:String, val userName:String?, val pswd:String?, val maxConnPoolSize:Int){
    fun createDataSource():HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = jdbcURL
        config.username=userName
        config.password=pswd
        config.maximumPoolSize = maxConnPoolSize
        config.validate()
        return HikariDataSource(config)
    }
}