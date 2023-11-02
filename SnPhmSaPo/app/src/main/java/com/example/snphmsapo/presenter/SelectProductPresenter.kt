package com.example.snphmsapo.presenter

import android.content.Context
import com.example.snphmsapo.api.RetrofitCall
import com.example.snphmsapo.api.model.datalistvariant.ListVariantData
import com.example.snphmsapo.contract.SelectProductContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectProductPresenter(private val showData: SelectProductContract.ShowData):SelectProductContract.Presenter {
    override fun callApiVariantProduct(page: Int, query: String) {
        RetrofitCall.apiService.getListVariantProduct(page, 10,query)
            .enqueue(object : Callback<ListVariantData> {
                override fun onResponse(
                    call: Call<ListVariantData>,
                    response: Response<ListVariantData>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        data?.let {
                            getDataVariant(data)
                        }
                    } else {
                        showData.showError()
                    }
                }

                override fun onFailure(call: Call<ListVariantData>, t: Throwable) {
                    showData.showError()
                }
            })
    }

    fun getDataVariant(data:ListVariantData){
        showData.showDataVariantProduct(com.example.snphmsapo.model.ListVariant.getListVariant(data))
    }
}