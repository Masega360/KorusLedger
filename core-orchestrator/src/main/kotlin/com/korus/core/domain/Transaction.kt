package com.korus.core.domain
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: UUID,
    val title: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val category: String,
    val date: LocalDateTime
)
