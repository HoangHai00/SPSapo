package com.example.snphmsapo.contract

interface ListProductContract {
    interface ShowData {
        fun showDataProduct(data: com.example.snphmsapo.model.ListProduct)
        fun showDataVariantProduct(data: com.example.snphmsapo.model.ListVariant)
        fun showError()
    }

    interface Presenter {
        fun callApiProduct(page: Int,query:String)
        fun callApiVariantProduct(page: Int,query:String)
    }
}