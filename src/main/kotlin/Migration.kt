import appconfig.AppConfig
import appconfig.AppConfig.Companion.ALERTS_SCHEMA_NAME
import io.ktor.server.application.*
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import models.repositories.tables.Tasks
import mu.KLogger
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import java.sql.DriverManager


fun Application.initDb() {
    val appConfig: AppConfig by inject()
    createDatabase(appConfig)
    createSchema(appConfig)
    liquibaseMigrate(appConfig)
}

private val logger: KLogger = KotlinLogging.logger {}

fun createDatabase(appConfig: AppConfig){
    logger.info("Creating database")
    try {
        val con = DriverManager.getConnection(appConfig.dbConfig.jdbcURL, appConfig.dbConfig.userName, appConfig.dbConfig.pswd)
        con.prepareStatement("CREATE DATABASE $ALERTS_SCHEMA_NAME").execute()
        logger.info("Created database $ALERTS_SCHEMA_NAME")
    } catch (e: Exception) {
        val message = "database \"$ALERTS_SCHEMA_NAME\" already exists"
        if (e.message!!.contains(message)) {
            logger.info(message)
        } else {
            logger.error(e.message, e)
        }
    }
}

fun createSchema(appConfig: AppConfig){
    logger.info("Creating database")
    try {
        val con = DriverManager.getConnection(appConfig.dbConfig.jdbcURL, appConfig.dbConfig.userName, appConfig.dbConfig.pswd)
        con.prepareStatement("CREATE SCHEMA $ALERTS_SCHEMA_NAME").execute()
        logger.info("Created schema $ALERTS_SCHEMA_NAME")
    } catch (e: Exception) {
        val message = "schema \"$ALERTS_SCHEMA_NAME\" already exists"
        if (e.message!!.contains(message)) {
            logger.info(message)
        } else {
            logger.error(e.message, e)
        }
    }
}

private fun liquibaseMigrate(appConfig: AppConfig) {
    val connection = DriverManager.getConnection(appConfig.dbConfig.jdbcURL, appConfig.dbConfig.userName, appConfig.dbConfig.pswd)

    try {
        val database: Database =
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection))
        database.defaultSchemaName = ALERTS_SCHEMA_NAME
        val liquibase = Liquibase("db/liquibase-changelog-master.xml", ClassLoaderResourceAccessor(), database)
        liquibase.update(Contexts())
//        liquibase.update()
        logger.info("Created Table")
    } catch (e: Exception) {
        val message = "error creating table tasks "
        if (e.message!!.contains(message)) {
            logger.info(message)
        } else {
            logger.error(e.message, e)
        }
    }finally {
        if (connection != null) {
            connection.rollback()
            connection.close()
        }
    }
}