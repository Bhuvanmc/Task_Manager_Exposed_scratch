import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
//    kotlin("jvm") version "1.6.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"
val exposed_version : String by project
val ktor_version : String by project
val hikaricp_version: String by project
val ehcache_version: String by project
val koin_version : String by project
val koin_ktor: String by project
val liquibaseVersion: String by project
//val logbackVersion = "1.2.3"
//val ktorVersion = "1.6.8"
//val koinVersion = "3.1.6"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core:2.2.2")
    implementation("io.ktor:ktor-server-netty:2.2.2")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("org.ehcache:ehcache:$ehcache_version")
    implementation("io.github.microutils:kotlin-logging:1.12.0")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")


//    Koin
    // Koin Core features
    implementation("io.insert-koin:koin-core:$koin_version")
// Koin Test features
//    testImplementation("io.insert-koin:koin-test:$koin_version")
    // Koin for Ktor
    implementation ("io.insert-koin:koin-ktor:$koin_ktor")
// SLF4J Logger
    implementation ("io.insert-koin:koin-logger-slf4j:$koin_ktor")
// logback
    implementation("ch.qos.logback:logback-classic:1.4.5")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.0")

    testImplementation("io.zonky.test:embedded-postgres:2.0.2")

    // https://mvnrepository.com/artifact/io.zonky.test.postgres/embedded-postgres-binaries-darwin-arm64v8
//    testImplementation("io.zonky.test.postgres:embedded-postgres-binaries-darwin-arm64v8:14.6.0")



////     https://mvnrepository.com/artifact/org.slf4j/slf4j-api
//    implementation("org.slf4j:slf4j-api:2.0.6")
//// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api
//    implementation("org.apache.logging.log4j:log4j-api:2.19.0")
//// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
//    implementation("org.apache.logging.log4j:log4j-core:2.19.0")
//// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j-impl
//    testImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.19.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}