package com.korus.core.application

import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class RecordTransactionUseCaseTest {

    // 1. EL DOBLE DE RIESGO: Un repositorio falso que guarda cosas en la memoria RAM
    class FakeTransactionRepository : TransactionRepository {
        val savedTransactions = mutableListOf<Transaction>()

        override fun save(transaction: Transaction): Transaction {
            savedTransactions.add(transaction) // Lo guardamos en la lista
            return transaction
        }

        override fun findAll(): List<Transaction> = savedTransactions
    }

    @Test
    fun `execute should create and save a transaction successfully`() {
        // --- ARRANGE (Preparar el escenario) ---
        val fakeRepo = FakeTransactionRepository()
        val useCase = RecordTransactionUseCase(fakeRepo)

        // --- ACT (Acción: Ejecutar el código a testear) ---
        val result = useCase.execute(
            title = "Cena de festejo",
            amount = BigDecimal("15000.50"),
            type = TransactionType.EXPENSE,
            category = "Comida"
        )

        // --- ASSERT (Verificar que todo salió bien) ---
        // 1. Verificamos que el Caso de Uso haya autogenerado el ID
        assertNotNull(result.id) 
        
        // 2. Verificamos que no nos haya cambiado los datos
        assertEquals("Cena de festejo", result.title)
        assertEquals(BigDecimal("15000.50"), result.amount)
        
        // 3. Verificamos LA REGLA DE NEGOCIO: ¿Le dijo al repositorio que lo guarde?
        assertEquals(1, fakeRepo.savedTransactions.size) 
        assertEquals(result.id, fakeRepo.savedTransactions.first().id)
    }
}