package com.example.snphmsapo.api.model.datalistproduct

data class ProductData(
    val brand: String?,
    val category: String?,
    val category_code: Any,
    val created_on: String,
    val description: String,
    val id: Int,
    val image_name: Any,
    val image_path: Any,
    val images: List<ImageData>,
    val medicine: Boolean,
    val modified_on: String,
    val name: String,
    val opt1: String,
    val opt2: Any,
    val opt3: Any,
    val options: List<OptionData>,
    val product_medicines: Any,
    val product_type: String,
    val status: String,
    val tags: String,
    val tenant_id: Int,
    val variants: List<VariantData>
){

}