package ru.wallmaster.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.wallmaster.*
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import javax.imageio.ImageIO


fun Application.configureRouting() {
    routing {
        val groups = GroupTable.fetchAll()
        val group = ImageTable.fetchAll()

        get("/groups/fetch/groupsImg") {
            val imgGroups = createImageList(groups)
            call.respond(imgGroups)
        }
        get("/groups/fetch/titles") {
            val imgGroups = createTitleList(groups)
            call.respond(imgGroups)
        }
        get("/groups/fetch/анимеInfo") {
            val tagsGroup = createWallTags(group, "anime")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/анимеImg") {
            val imgGroup = createWallImg(group, "anime")
            call.respond(imgGroup)
        }
        get("/groups/fetch/машиныInfo") {
            val tagsGroup = createWallTags(group, "car")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/машиныImg") {
            val imgGroup = createWallImg(group, "car")
            call.respond(imgGroup)
        }
        get("/groups/fetch/котыInfo") {
            val tagsGroup = createWallTags(group, "cat")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/котыImg") {
            val imgGroup = createWallImg(group, "cat")
            call.respond(imgGroup)
        }
        get("/groups/fetch/игрыInfo") {
            val tagsGroup = createWallTags(group, "game")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/игрыImg") {
            val imgGroup = createWallImg(group, "game")
            call.respond(imgGroup)
        }
        get("/groups/fetch/природаInfo") {
            val tagsGroup = createWallTags(group, "nature")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/природаImg") {
            val imgGroup = createWallImg(group, "nature")
            call.respond(imgGroup)
        }
        get("/groups/fetch/россияInfo") {
            val tagsGroup = createWallTags(group, "russia")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/россияImg") {
            val imgGroup = createWallImg(group, "russia")
            call.respond(imgGroup)
        }
        get("/groups/fetch/сериалыInfo") {
            val tagsGroup = createWallTags(group, "series")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/сериалыImg") {
            val imgGroup = createWallImg(group, "series")
            call.respond(imgGroup)
        }
        get("/groups/fetch/спортInfo") {
            val tagsGroup = createWallTags(group, "sport")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/спортImg") {
            val imgGroup = createWallImg(group, "sport")
            call.respond(imgGroup)
        }
        get("/groups/fetch/татуInfo") {
            val tagsGroup = createWallTags(group, "tattoo")
            call.respond(tagsGroup)
        }
        get("/groups/fetch/татуImg") {
            val imgGroup = createWallImg(group, "tattoo")
            call.respond(imgGroup)
        }
        post("/search") {
            val receive = call.receive(SearchReceiveRemote::class).tag
            val list = searchImg(group, receive)
            call.respond(mapOf("search" to list))
        }
        
    }
}

fun createImageList(groups: List<GroupDTO>): MutableMap<String, MutableList<ByteArray>> {
    val map = mutableMapOf("paths" to mutableListOf<ByteArray>())
    groups.forEach {
        val imgFile = File(it.path)
        val img = ImageIO.read(imgFile)
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(img, "jpg", outputStream)
        val bytes = outputStream.toByteArray()
        map["paths"]?.add(bytes)
    }


    return map
}

fun createTitleList(groups: List<GroupDTO>): MutableMap<String, MutableList<String>> {
    val map = mutableMapOf("titles" to mutableListOf<String>())

    groups.forEach {
        map["titles"]?.add(it.name)
    }

    return map
}

fun createWallTags(groups: List<ImageDTO>, name: String): MutableMap<String, MutableList<String>> {
    val map = mutableMapOf("tags" to mutableListOf<String>())

    groups.forEach {
        if (name == it.group) map["tags"]?.add(it.tags)
    }

    return map
}

fun createWallImg(groups: List<ImageDTO>, name: String): MutableMap<String, MutableList<ByteArray>> {
    val map = mutableMapOf("img" to mutableListOf<ByteArray>())

    groups.forEach{
        if (name == it.group) {
            val imgFile = File(it.path)
            val img = ImageIO.read(imgFile)
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(img, "jpg", outputStream)
            val bytes = outputStream.toByteArray()
            map["img"]?.add(bytes)
        }
    }

    return map
}

fun searchImg(groups: List<ImageDTO>, group: String): MutableList<SearchResponseRemote>{
    val list = mutableListOf<SearchResponseRemote>()

    groups.forEach {
        if (group in it.tags) {
            val imgFile = File(it.path)
            val img = ImageIO.read(imgFile)
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(img, "jpg", outputStream)
            val bytes = outputStream.toByteArray()
            list.add(SearchResponseRemote(it.tags, bytes))
        }
    }

    return list
}