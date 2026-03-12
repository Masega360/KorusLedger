package com.korus.core.application

import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionCategory
import com.korus.core.domain.TransactionType
import java.time.LocalDateTime
import java.util.UUID

interface TransactionRepository {
    fun save(transaction: Transaction): Transaction

    fun findAll(
        userId: UUID,
        type: TransactionType? = null,
        category: TransactionCategory? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null
    ): List<Transaction>
}