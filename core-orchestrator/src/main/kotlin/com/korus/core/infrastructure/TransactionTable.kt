package com.korus.core.infrastructure

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object TransactionTable: Table("transactions"){
    val id = uuid("id")
    val title = varchar("title",255)
    val amount = decimal("amount", 12, 2)
    val type = varchar("type",50)
    val category = varchar("category", 255)
    val date = datetime("date")
    override val primaryKey = PrimaryKey(id, name = "PK_Transaction_ID")
}