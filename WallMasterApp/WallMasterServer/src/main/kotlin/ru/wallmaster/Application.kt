package ru.wallmaster

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.wallmaster.plugins.*
import java.io.File
import javax.imageio.ImageIO

fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql.Driver",
        user = "postgres", password = "zhjckfdcsy2003")

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)


}

fun Application.module() {
    configureRouting()
    configureSerialization()
}

