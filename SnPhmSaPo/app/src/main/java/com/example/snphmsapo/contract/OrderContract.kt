package com.example.snphmsapo.contract


import com.example.snphmsapo.model.Order

interface OrderContract {
    interface ShowData {
        fun showError()
        fun success()
    }

    interface Presenter {
        fun postOrder(order: Order)
    }
}