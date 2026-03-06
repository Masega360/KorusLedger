package com.korus.core.application
import com.korus.core.domain.Transaction
interface  TransactionRepository{
    fun save(transaction: Transaction): Transaction
    fun findAll(): List<Transaction>
}