package com.example.snphmsapo.api

import com.example.snphmsapo.api.model.datalistproduct.ListProductData
import com.example.snphmsapo.api.model.datalistvariant.ListVariantData
import com.example.snphmsapo.api.model.dataproductdetail.ProductDetailData
import com.example.snphmsapo.api.model.datavariantdetail.VariantDetailData
import com.example.snphmsapo.api.model.order.OrderRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // api sản phẩm
    @GET("admin/products.json")
    fun getListProduct(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("query") query: String
    ): Call<ListProductData>

    // api phiên bản sản phẩm
    @GET("admin/variants/search.json")
    fun getListVariantProduct(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("query") query: String
    ): Call<ListVariantData>

    // api chi tiết sản phẩm
    @GET("admin/products/{id}.json")
    fun getProductDetail(
        @Path("id") id: Int
    ): Call<ProductDetailData>

    @GET("admin/products/{product_id}/variants/{variant_id}.json")
    fun getVariantProductDetail(
        @Path("product_id") idProduct: Int,
        @Path("variant_id") idVariant: Int
    ): Call<VariantDetailData>

    @POST("admin/orders.json")
    fun postOrder(@Body orderRequest: OrderRequest): Call<OrderRequest>

}


