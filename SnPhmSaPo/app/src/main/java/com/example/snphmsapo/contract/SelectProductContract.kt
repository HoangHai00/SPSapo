package com.example.snphmsapo.contract

interface SelectProductContract {
    interface ShowData {
        fun showDataVariantProduct(data: com.example.snphmsapo.model.ListVariant)
        fun showError()
    }

    interface Presenter {
        fun callApiVariantProduct(page: Int,query:String)
    }
}