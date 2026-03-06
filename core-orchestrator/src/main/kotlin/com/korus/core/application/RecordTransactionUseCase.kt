package com.korus.core.application
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import com.korus.core.domain.*
class RecordTransactionUseCase(
        private val repository: TransactionRepository
){
    public fun execute(title: String, amount: BigDecimal, type: TransactionType, category: String): Transaction{
        val transaction =  Transaction(id = UUID.randomUUID(), title, amount, type, category, date = LocalDateTime.now())
        return repository.save(transaction)
    }
}