package com.korus.core.api

import com.korus.core.application.GetTransactionsUseCase
import com.korus.core.application.RecordTransactionUseCase
import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionCategory
import com.korus.core.domain.TransactionType
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    // Inyectamos ambos acá arriba, una sola vez.
    private val recordUseCase: RecordTransactionUseCase,
    private val getUseCase: GetTransactionsUseCase
) {

    @PostMapping
    fun create(@RequestBody request: TransactionRequest): Transaction {
        return recordUseCase.execute(
            request.title,
            request.amount,
            request.type,
            request.category
        )
    }

    @GetMapping
    fun getAll(
        @RequestParam(required = false) type: TransactionType?,
        @RequestParam(required = false) category: TransactionCategory?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDate: LocalDateTime?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDate: LocalDateTime?
    ): List<Transaction> {
        return getUseCase.execute(type, category, startDate, endDate)
    }
}