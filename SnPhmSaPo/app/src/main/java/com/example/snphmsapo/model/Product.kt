package com.example.snphmsapo.model

import com.example.snphmsapo.api.model.datalistproduct.OptionData
import com.example.snphmsapo.api.model.datalistproduct.ProductData


data class Product(
    var brand: String? = null,
    var category: String? = null,
    var category_code: Any? = null,
    var created_on: String? = null,
    var description: String? = null,
    var id: Int? = null,
    var image_name: Any? = null,
    var image_path: Any? = null,
    var images: List<Image>? = null,
    var medicine: Boolean? = null,
    var modified_on: String? = null,
    var name: String? = null,
    var opt1: String? = null,
    var opt2: Any? = null,
    var opt3: Any? = null,
    var options: List<OptionData>? = null,
    var product_medicines: Any? = null,
    var product_type: String? = null,
    var status: String? = null,
    var tags: String? = null,
    var tenant_id: Int? = null,
    var variants: List<Variant>? = null
) {
    companion object {
        fun getProduct(
            apiProducts: List<ProductData>,
        ): List<Product> {
            return apiProducts.map { apiProduct ->
                Product(
                    id = apiProduct.id,
                    brand = apiProduct.brand,
                    category = apiProduct.category,
                    description = apiProduct.description,
                    images = Image.getImage(apiProduct.images),
                    name = apiProduct.name,
                    status = apiProduct.status,
                    variants = Variant.getVariant(apiProduct.variants)
                )
            }
        }

        fun getProductDetail(
            apiProduct: ProductData
        ): Product {
            return Product(
                id = apiProduct.id,
                brand = apiProduct.brand,
                category = apiProduct.category,
                description = apiProduct.description,
                images = Image.getImage(apiProduct.images),
                name = apiProduct.name,
                status = apiProduct.status,
                variants = Variant.getVariant(apiProduct.variants)
            )
        }

    }



}

