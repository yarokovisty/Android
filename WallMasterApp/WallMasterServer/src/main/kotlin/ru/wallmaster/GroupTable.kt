package ru.wallmaster

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception


object GroupTable : Table() {
    val groupId = integer("id")
    val name = varchar("name", 50)
    val path = varchar("path", 300)

    fun fetchAll(): List<GroupDTO> {
        return try {
            transaction {
                GroupTable.selectAll().toList()
                    .map {
                        GroupDTO(
                            groupId = it[groupId],
                            name = it[name],
                            path = it[path]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}