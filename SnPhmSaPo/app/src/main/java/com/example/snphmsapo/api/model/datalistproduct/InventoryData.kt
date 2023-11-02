package com.example.snphmsapo.api.model.datalistproduct

data class InventoryData(
    val amount: Int,
    val available: Double,
    val bin_location: Any,
    val committed: Double,
    val incoming: Double,
    val location_id: Int,
    val mac: Double,
    val max_value: Any,
    val min_value: Any,
    val modified_on: Any,
    val on_hand: Double,
    val onway: Double,
    val variant_id: Int,
    val wait_to_pack: Double
)