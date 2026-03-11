package com.korus.core.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class TransactionTest {

    @Test
    fun `should create a valid transaction entity`() {
        // Arrange & Act
        val transaction = Transaction(
            id = UUID.randomUUID(),
            title = "Compra de teclado",
            amount = BigDecimal("85000.00"),
            type = TransactionType.EXPENSE,
            category = "Tecnología",
            date = LocalDateTime.now()
        )

        // Assert
        assertNotNull(transaction.id)
        assertNotNull(transaction.date)
        assertEquals("Compra de teclado", transaction.title)
        assertEquals(TransactionType.EXPENSE, transaction.type)
        assertEquals(BigDecimal("85000.00"), transaction.amount)
    }
}