package com.korus.core.api

import java.math.BigDecimal
import com.korus.core.domain.TransactionType
data class TranscationRequest(
    val title: String,
    val ammount: BigDecimal,
    val type: TransactionType,
    val category: String,
)