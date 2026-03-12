package com.korus.core.application


import com.korus.core.domain.Wallet
import com.korus.core.domain.WalletType
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.math.BigDecimal
import java.util.UUID

interface WalletRepository {
    fun save(wallet: Wallet): Wallet

    fun findAll(
        userId: UUID,
        type: WalletType? = null,
        balance: BigDecimal? = null,
    ): List<Wallet>
}