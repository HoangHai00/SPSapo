package com.example.snphmsapo.model

class ProductDetail(
    val product: Product
) {
    companion object {
        fun getProductDetail(
            apiProductDetail: com.example.snphmsapo.api.model.dataproductdetail.ProductDetailData
        ): ProductDetail {
            return ProductDetail(
                product = Product.getProductDetail(apiProductDetail.product)
            )
        }
    }
}