package com.korus.core.infrastructure

import org.jetbrains.exposed.sql.Table


object WalletTable : Table("wallets") {
    val walletId = uuid("id")
    val userId = uuid("user_id")
    val name = varchar("name", 255)
    val balance = decimal("balance", 12, 2)
    val type = varchar("type", 50)
    override val primaryKey = PrimaryKey(walletId, name = "PK_Wallet_ID")
}