package com.example.snphmsapo.contract

import com.example.snphmsapo.model.ProductDetail
import com.example.snphmsapo.model.VariantDetail


interface VariantProductDetailContract {
    interface ShowData {
        fun showError()
        fun showDataVariant(data: VariantDetail)
        fun showDataProduct(data: ProductDetail)
    }

    interface Presenter {
        fun callApiProduct(idProduct: Int)
        fun callApiVariant(idProduct: Int, idVariant: Int)
    }
}