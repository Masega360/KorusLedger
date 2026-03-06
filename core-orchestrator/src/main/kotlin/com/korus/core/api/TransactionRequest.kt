package com.korus.core.api

import java.math.BigDecimal
import com.korus.core.domain.TransactionType
data class TransactionRequest(
    val title: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val category: String,
)