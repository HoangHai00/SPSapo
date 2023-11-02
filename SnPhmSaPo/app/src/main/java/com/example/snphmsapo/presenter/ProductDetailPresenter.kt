package com.example.snphmsapo.presenter

import com.example.snphmsapo.api.model.dataproductdetail.ProductDetailData
import com.example.snphmsapo.api.RetrofitCall
import com.example.snphmsapo.contract.ProductDetailContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailPresenter(private val showData: ProductDetailContract.ShowData) :
    ProductDetailContract.Presenter {

    override fun callApi(id: Int) {
        RetrofitCall.apiService.getProductDetail(id).enqueue(object : Callback<ProductDetailData> {
            override fun onResponse(call: Call<ProductDetailData>, response: Response<ProductDetailData>) {
                if (response.isSuccessful) {
                    val productDetail = response.body()
                    productDetail?.let {
                        getData(productDetail)
                    }
                } else {
                    showData.showError()
                }
            }

            override fun onFailure(call: Call<ProductDetailData>, t: Throwable) {
                showData.showError()
            }

        })

    }

    private fun getData(data:ProductDetailData) {
        showData.showData(com.example.snphmsapo.model.ProductDetail.getProductDetail(data))
    }


}