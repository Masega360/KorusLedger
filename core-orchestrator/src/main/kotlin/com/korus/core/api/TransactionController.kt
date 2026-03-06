package com.korus.core.api
import org.springframework.web.bind.annotation.*


import com.korus.core.application.RecordTransactionUseCase
import com.korus.core.domain.Transaction

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val recordTransactionUseCase: RecordTransactionUseCase
) {

    @PostMapping
    fun createTransaction(@RequestBody request: TransactionRequest):Transaction {
        return recordTransactionUseCase.execute(
        request.title, 
        request.amount, 
        request.type, 
        request.category)
    }
}