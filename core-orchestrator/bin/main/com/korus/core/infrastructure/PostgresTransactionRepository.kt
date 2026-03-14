package com.korus.core.infrastructure

import com.korus.core.application.TransactionRepository
import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionCategory
import com.korus.core.domain.TransactionType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime
import java.util.UUID

@Repository
class PostgresTransactionRepository : TransactionRepository {

    override fun save(transaction: Transaction): Transaction {
    transaction {
        TransactionTable.insert { row ->
            row[id] = transaction.id
            row[title] = transaction.title
            row[amount] = transaction.amount
            row[type] = transaction.type.name
            row[category] = transaction.category.name
            row[date] = transaction.date
            row[walletId] = transaction.walletId 
            row[userId] = transaction.userId     
        }
    }
    return transaction
}

    override fun findAll(
        userId: UUID,
        type: TransactionType?,
        category: TransactionCategory?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<Transaction> {
        return transaction {
            var query = TransactionTable.select {TransactionTable.userId eq userId }

            type?.let { query = query.andWhere { TransactionTable.type eq it.name } }
            category?.let { query = query.andWhere { TransactionTable.category eq it.name } }
            startDate?.let { query = query.andWhere { TransactionTable.date greaterEq it } }
            endDate?.let { query = query.andWhere { TransactionTable.date lessEq it } }

            query.orderBy(TransactionTable.date to SortOrder.DESC)
                .map { row ->
                    Transaction(
                        id = row[TransactionTable.id],
                        title = row[TransactionTable.title],
                        amount = row[TransactionTable.amount],
                        type = TransactionType.valueOf(row[TransactionTable.type]),
                        category = TransactionCategory.valueOf(row[TransactionTable.category]),
                        date = row[TransactionTable.date],
                        walletId = row[TransactionTable.walletId],
                        userId = row[TransactionTable.userId],

                    )
                }
        }
    }
}