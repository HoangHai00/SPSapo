package com.example.snphmsapo.presenter

import com.example.snphmsapo.api.model.dataproductdetail.ProductDetailData
import com.example.snphmsapo.api.model.datavariantdetail.VariantDetailData
import com.example.snphmsapo.api.RetrofitCall
import com.example.snphmsapo.contract.VariantProductDetailContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VariantDetailPresenter(
    private val showData: VariantProductDetailContract.ShowData
) :
    VariantProductDetailContract.Presenter {

    override fun callApiProduct(idProduct: Int) {
        RetrofitCall.apiService.getProductDetail(idProduct)
            .enqueue(object : Callback<ProductDetailData> {
                override fun onResponse(
                    call: Call<ProductDetailData>,
                    response: Response<ProductDetailData>
                ) {
                    if (response.isSuccessful) {
                        val productDetail = response.body()
                        productDetail?.let {
                            getDataProduct(productDetail)
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


    override fun callApiVariant(idProduct: Int, idVariant: Int) {
        RetrofitCall.apiService.getVariantProductDetail(idProduct, idVariant).enqueue(object :
            Callback<VariantDetailData> {
            override fun onResponse(
                call: Call<VariantDetailData>,
                response: Response<VariantDetailData>
            ) {
                if (response.isSuccessful) {
                    val varianDetail = response.body()
                    varianDetail?.let {
                        getDataVariant(varianDetail)
                    }
                } else {
                    showData.showError()
                }
            }

            override fun onFailure(call: Call<VariantDetailData>, t: Throwable) {
                showData.showError()
            }

        })
    }

    private fun getDataVariant(data: VariantDetailData) {
        showData.showDataVariant(
            com.example.snphmsapo.model.VariantDetail.getVariantDetail(
                data
            )
        )
    }

    private fun getDataProduct(data: ProductDetailData) {
        showData.showDataProduct(
            com.example.snphmsapo.model.ProductDetail.getProductDetail(
                data
            )
        )
    }


}

