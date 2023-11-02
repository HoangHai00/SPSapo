package com.example.snphmsapo.presenter

import com.example.snphmsapo.contract.ListProductContract
import com.example.snphmsapo.api.model.datalistproduct.ListProductData
import com.example.snphmsapo.api.model.datalistvariant.ListVariantData
import com.example.snphmsapo.api.RetrofitCall
import com.example.snphmsapo.model.ListProduct
import com.example.snphmsapo.model.ListVariant

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListProductPresenter(private val showData: ListProductContract.ShowData) :
    ListProductContract.Presenter {
    override fun callApiProduct(page: Int,query:String) {
        RetrofitCall.apiService.getListProduct(page, 10, query).enqueue(object : Callback<ListProductData> {
            override fun onResponse(call: Call<ListProductData>, response: Response<ListProductData>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        getDataProduct(it)
                    }
                } else {
                    showData.showError()
                }
            }

            override fun onFailure(call: Call<ListProductData>, t: Throwable) {
                showData.showError()
            }

        })
    }

    override fun callApiVariantProduct(page: Int,query:String) {
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
    fun getDataProduct(data:ListProductData){
        showData.showDataProduct(ListProduct.getListProduct(data))
    }
    fun getDataVariant(data:ListVariantData){
        showData.showDataVariantProduct(ListVariant.getListVariant(data))
    }

}

