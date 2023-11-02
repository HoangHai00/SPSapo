package com.example.snphmsapo.contract

import com.example.snphmsapo.model.ProductDetail

interface ProductDetailContract {
    interface ShowData {
        fun showError()
        fun showData(productDetail: ProductDetail)
    }

    interface Presenter {
        fun callApi(id: Int)
    }
}