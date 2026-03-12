package com.korus.core.api

import com.korus.core.application.GetBalanceUseCase
import com.korus.core.application.GetTransactionsUseCase
import com.korus.core.application.RecordTransactionUseCase
import com.korus.core.domain.Transaction
import com.korus.core.domain.TransactionCategory
import com.korus.core.domain.TransactionType
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val recordUseCase: RecordTransactionUseCase,
    private val getUseCase: GetTransactionsUseCase,
    private val getBalanceUseCase: GetBalanceUseCase
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
    @GetMapping("/balance")
    fun getBalance(): Map<String, BigDecimal> {
        return mapOf("balance" to getBalanceUseCase.execute())
    }
}