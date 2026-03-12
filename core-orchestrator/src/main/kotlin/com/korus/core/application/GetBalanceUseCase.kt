package com.korus.core.application

import com.korus.core.domain.TransactionType
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class GetBalanceUseCase(
    private val repository: TransactionRepository
) {
    fun execute(): BigDecimal {
        val transactions = repository.findAll()

        return transactions.fold(BigDecimal.ZERO) { acc, transaction ->
            when (transaction.type) {
                TransactionType.INCOME -> acc.add(transaction.amount)
                TransactionType.EXPENSE -> acc.subtract(transaction.amount)
            }
        }
    }
}