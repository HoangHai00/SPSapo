package com.example.snphmsapo.model

// chuyển về doble
data class Inventory(
    var amount: Int? = null,
    var available: Double? = null,
    var bin_location: Any? = null,
    var committed: Double? = null,
    var incoming: Double? = null,
    var location_id: Int? = null,
    var mac: Double? = null,
    var max_value: Any? = null,
    var min_value: Any? = null,
    var modified_on: Any? = null,
    var on_hand: Double? = null,
    var onway: Double? = null,
    var variant_id: Int? = null,
    var wait_to_pack: Double? = null,
) {
    companion object {
        fun getInventories(apiInventories: List<com.example.snphmsapo.api.model.datalistproduct.InventoryData>): List<Inventory> {
            return apiInventories.map { apiInventory ->
                Inventory(
                    on_hand = apiInventory.on_hand ,
                    available = apiInventory.available
                )
            }
        }


    }


}
