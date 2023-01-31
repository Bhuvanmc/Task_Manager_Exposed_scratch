package services.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.*
import models.Tasks
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DatabaseFactory:KoinComponent {
//    fun init(config:ApplicationConfig) {
//        val driverClassName=""
//        val jdbcURL= config.property("storage.jdbcURL").getString()
//        val username=config.property("storage.username").getString()
//        val pswd=config.property("storage.pswd").getString()
////        val database = Database.connect(jdbcUrl)
//
////        val database = Database.connect(jdbcURL, user = username, password = pswd)
//        val database = Database.connect(createHikariDataSource(jdbcURL, username, pswd))
//        transaction(database) {
//            SchemaUtils.create(Tasks)
//        }
//    }

//  By using Koin dependency injection
    fun init(database:Database) {

        transaction(database) {
            SchemaUtils.create(Tasks)
        }
    }

//    fun createHikariDataSource(jdbcURL:String, username:String, pswd:String)=
//        HikariDataSource(HikariConfig().apply {
//            jdbcUrl=jdbcURL
//            maximumPoolSize = 3
//            setUsername(username)
//            password = pswd
//            validate()
//        })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }



}