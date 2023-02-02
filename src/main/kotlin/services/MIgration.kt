package services

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import models.repositories.tables.Tasks
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.initDb(database: Database) {
        transaction(database) {
            SchemaUtils.create(Tasks)
        }
    }


