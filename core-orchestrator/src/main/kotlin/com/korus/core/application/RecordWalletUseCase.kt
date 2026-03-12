package com.korus.core.application

import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionCategory
import com.korus.core.domain.TransactionType
import com.korus.core.domain.Wallet
import com.korus.core.domain.WalletType
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class RecordWalletUseCase (
    private val repository: WalletRepository
    ){
        public fun execute(userId: UUID, name: String, balance: BigDecimal, type: WalletType): Wallet{

            val wallet = Wallet(
                walletId = UUID.randomUUID(),
                name = name,
                balance = balance,
                type = type,
                userId = userId,
            )
            return repository.save(wallet)
        }
}