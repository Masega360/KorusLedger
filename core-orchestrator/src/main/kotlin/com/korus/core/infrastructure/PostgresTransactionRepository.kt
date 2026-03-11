package com.korus.core.infrastructure

import com.korus.core.application.TransactionRepository
import com.korus.core.domain.Transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class PostgresTransactionRepository : TransactionRepository {

    override fun save(transaction: Transaction): Transaction {
        transaction {
            TransactionTable.insert { row ->
                row[id] = transaction.id
                row[title] = transaction.title
                row[amount] = transaction.amount
                row[type] = transaction.type.name
                row[category] = transaction.category
                row[date] = transaction.date
            }
        }
        return transaction
    }

    override fun findAll(): List<Transaction> {
        return emptyList()
    }
}