package com.example.snphmsapo.presenter

import android.content.Context
import com.example.snphmsapo.api.RetrofitCall
import com.example.snphmsapo.api.model.order.OrderRequest
import com.example.snphmsapo.contract.OrderContract
import com.example.snphmsapo.contract.SelectProductContract
import com.example.snphmsapo.model.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPresenter(private val showData: OrderContract.ShowData) :
    OrderContract.Presenter {
    override fun postOrder(order: Order) {
        val orderRequest = OrderRequest.getOrder(order)
        RetrofitCall.apiService.postOrder(orderRequest).enqueue(object : Callback<OrderRequest> {
            override fun onResponse(call: Call<OrderRequest>, response: Response<OrderRequest>) {
                    if (response.isSuccessful){
                        val data = response.body()
                        data?.let {
                        showData.success()
                        }
                    }
                else{
                    showData.showError()
                    }
            }

            override fun onFailure(call: Call<OrderRequest>, t: Throwable) {
                showData.showError()
            }

        })
    }
}