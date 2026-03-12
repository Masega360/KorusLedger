package com.korus.core.application
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import com.korus.core.domain.*
import org.springframework.stereotype.Service

@Service
class RecordTransactionUseCase(
        private val repository: TransactionRepository
){
    public fun execute(title: String, amount: BigDecimal, type: TransactionType, category: TransactionCategory): Transaction{
        val transaction =  Transaction(id = UUID.randomUUID(), title, amount, type, category, date = LocalDateTime.now())
        return repository.save(transaction)
    }
}