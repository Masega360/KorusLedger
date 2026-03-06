package com.korus.core.api
import TranscationRequest
import com.korus.core.application.RecordTransactionUseCase
data class TransactionController(
    val recordUseCase: RecordTransactionUseCase
)
{
    public fun createTransaction(request: TranscationRequest){
        
    }
}