package com.korus.core.infrastructure

import com.korus.core.application.WalletRepository
import com.korus.core.domain.Wallet
import com.korus.core.domain.WalletType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.UUID

@Repository
class PostgresWalletRepository : WalletRepository {
    override fun save(wallet: Wallet): Wallet {
        transaction {
            WalletTable.insert { row ->
                row[WalletTable.walletId] = wallet.walletId
                row[WalletTable.name] = wallet.name
                row[WalletTable.balance] = wallet.balance
                row[WalletTable.type] = wallet.type.name
                row[WalletTable.userId] = wallet.userId
            }
        }
        return wallet
    }

    override fun findAll(
        userId: UUID,
        type: WalletType?,
        balance: BigDecimal?
    ): List<Wallet> {
        return transaction {
            val query = WalletTable.select { WalletTable.userId eq userId }

            type?.let { query.andWhere { WalletTable.type eq it.name } }
            balance?.let { query.andWhere { WalletTable.balance eq balance } }

            query.orderBy(WalletTable.balance to SortOrder.DESC)
                .map { row ->
                    Wallet(
                        walletId = row[WalletTable.walletId],
                        name = row[WalletTable.name],
                        balance = row[WalletTable.balance],
                        type = WalletType.valueOf(row[WalletTable.type]),
                        userId = row[WalletTable.userId]
                    )
                }
        }
    }
}