package ru.wallmaster

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception


object ImageTable : Table() {
    val imageId = integer("id")
    val group = varchar("group", 50)
    val tags = varchar("tags", 300)
    val path = varchar("path", 500)

    fun fetchAll(): List<ImageDTO> {
        return try {
            transaction {
                ImageTable.selectAll().toList()
                    .map {
                        ImageDTO(
                            imageId = it[imageId],
                            group = it[group],
                            tags = it[tags],
                            path = it[path]
                        )
                    }
            }
        } catch (e: Exception) {
            println("Error fetching data from database: ${e.message}")
            emptyList()
        }
    }
}