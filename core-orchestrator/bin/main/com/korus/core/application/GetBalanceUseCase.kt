package com.korus.core.application

import com.korus.core.domain.TransactionType
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class GetBalanceUseCase(
    private val repository: TransactionRepository
) {
    fun execute(userId: UUID): BigDecimal {
        val transactions = repository.findAll(userId = userId)

        return transactions.fold(BigDecimal.ZERO) { acc, transaction ->
            when (transaction.type) {
                TransactionType.INCOME -> acc.add(transaction.amount)
                TransactionType.EXPENSE -> acc.subtract(transaction.amount)
            }
        }
    }
}