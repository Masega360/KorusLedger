package com.korus.core.api

import com.korus.core.application.RecordWalletUseCase
import com.korus.core.domain.Wallet
import com.korus.core.domain.WalletType
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.UUID

data class WalletRequest(
    val name: String,
    val balance: BigDecimal,
    val type: WalletType
)

@RestController
@RequestMapping("/api/wallets")
class WalletController(
    private val recordWalletUseCase: RecordWalletUseCase
) {
    @PostMapping
    fun create(
        @RequestHeader("X-User-Id") userId: UUID,
        @RequestBody request: WalletRequest
    ): Wallet {
        return recordWalletUseCase.execute(
            userId = userId,
            name = request.name,
            balance = request.balance,
            type = request.type
        )
    }
}