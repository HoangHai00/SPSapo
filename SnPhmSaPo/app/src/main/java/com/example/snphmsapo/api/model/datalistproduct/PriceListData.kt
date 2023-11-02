package com.example.snphmsapo.api.model.datalistproduct

data class PriceListData(
    val code: String,
    val created_on: String,
    val currency_id: Int,
    val currency_iso: String,
    val currency_symbol: String,
    val id: Int,
    val `init`: Boolean,
    val is_cost: Boolean,
    val modified_on: String,
    val name: String,
    val status: String,
    val tenant_id: Int
)