package com.korus.core.application

import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionCategory
import com.korus.core.domain.TransactionType
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class GetTransactionsUseCase(private val repository: TransactionRepository) {
    fun execute(
        userId: UUID,
        type: TransactionType? = null,
        category: TransactionCategory? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null
    ): List<Transaction> = repository.findAll(userId, type, category, startDate, endDate) // Pasarlo acá
}