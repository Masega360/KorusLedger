package com.korus.core.domain
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: UUID,
    val title: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val category: TransactionCategory,
    val date: LocalDateTime,
    val walletId: UUID,
    val userId: UUID,
)

data class Wallet(
    val walletId: UUID,
    val userId: UUID,
    val name: String,
    val balance: BigDecimal,
    val type: WalletType
)
data class User(
    val userId: UUID,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val passwordHash: String,
    val createdAt: LocalDateTime,
)
