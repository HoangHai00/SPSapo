package com.example.snphmsapo.api.model.datalistproduct

data class VariantPriceData(
    val id: Int,
    val included_tax_price: Double,
    val name: String,
    val price_list: PriceListData,
    val price_list_id: Int,
    val value: Double
)