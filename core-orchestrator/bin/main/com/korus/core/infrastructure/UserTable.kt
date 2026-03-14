package com.korus.core.infrastructure

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable : Table("users") {
    val userId = uuid("id")
    val name = varchar("username", 128)
    val email = varchar("email", 255).uniqueIndex()
    val phoneNumber = varchar("phone_number", 255)
    val passwordHash = varchar("password_hash", 255)
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(userId, name = "PK_User_ID")
}