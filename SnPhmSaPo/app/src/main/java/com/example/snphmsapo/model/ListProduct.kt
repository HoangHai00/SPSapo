package com.example.snphmsapo.model

class ListProduct(
    var metadata: MetaData? = null,
    var products: List<Product>? = null
) {
    companion object {
        fun getListProduct(
            apiListProduct: com.example.snphmsapo.api.model.datalistproduct.ListProductData
        ): ListProduct {
            return ListProduct(
                metadata = MetaData.getMetaDataProduct(apiListProduct.metadata),
                products = Product.getProduct(apiListProduct.products)
            )
        }
    }


}


