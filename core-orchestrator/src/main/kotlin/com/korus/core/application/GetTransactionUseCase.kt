package com.korus.core.application

import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionCategory
import com.korus.core.domain.TransactionType
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GetTransactionsUseCase(private val repository: TransactionRepository) {
    fun execute(
        type: TransactionType? = null,
        category: TransactionCategory? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null
    ): List<Transaction> = repository.findAll(type, category, startDate, endDate)
}