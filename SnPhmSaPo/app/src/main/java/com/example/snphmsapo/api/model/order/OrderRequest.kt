package com.example.snphmsapo.api.model.order

import com.example.snphmsapo.model.Order

data class OrderRequest(
    val order: OrderData
) {

    companion object{
        fun getOrder(order: Order):OrderRequest{
            return OrderRequest(
                order = OrderData.getOrder(order)
            )
        }
    }
}