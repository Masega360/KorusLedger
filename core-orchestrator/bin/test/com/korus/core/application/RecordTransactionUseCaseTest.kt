package com.korus.core.application

import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionCategory
import com.korus.core.domain.TransactionType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class RecordTransactionUseCaseTest {

    class FakeTransactionRepository : TransactionRepository {
        val savedTransactions = mutableListOf<Transaction>()

        override fun save(transaction: Transaction): Transaction {
            savedTransactions.add(transaction)
            return transaction
        }

        override fun findAll(
            userId: UUID,
            type: TransactionType?,
            category: TransactionCategory?,
            startDate: LocalDateTime?,
            endDate: LocalDateTime?
        ): List<Transaction> = savedTransactions
    }

    @Test
    fun `execute should create and save a transaction successfully`() {
        val fakeRepo = FakeTransactionRepository()
        val useCase = RecordTransactionUseCase(fakeRepo)
        val testWalletId = UUID.randomUUID()
        val testUserId = UUID.randomUUID()

        val result = useCase.execute(
            title = "Cena de festejo",
            amount = BigDecimal("15000.50"),
            type = TransactionType.EXPENSE,
            category = TransactionCategory.FOOD,
            walletId = testWalletId,
            userId = testUserId
        )

        assertNotNull(result.id)
        assertEquals("Cena de festejo", result.title)
        assertEquals(BigDecimal("15000.50"), result.amount)
        assertEquals(testWalletId, result.walletId)
        assertEquals(testUserId, result.userId)
        assertEquals(1, fakeRepo.savedTransactions.size)
        assertEquals(result.id, fakeRepo.savedTransactions.first().id)
    }
}