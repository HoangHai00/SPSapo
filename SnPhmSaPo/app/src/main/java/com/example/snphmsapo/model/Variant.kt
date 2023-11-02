package com.example.snphmsapo.model

import com.example.snphmsapo.api.model.datalistproduct.VariantPriceData

// số lượng có thể bán và tồn kho là cộng dồn mảng invemtories
data class Variant(
    var barcode: String? = null,
    var brand_id: Any? = null,
    var category_id: Any? = null,
    var composite: Boolean? = null,
    var composite_items: Any? = null,
    var created_on: String? = null,
    var description: Any? = null,
    var expiration_alert_time: Any? = null,
    var id: Int? = null,
    var image_id: Int? = null,
    var images: List<ImageX>? = null,
    var init_price: Double? = null,
    var init_stock: Double? = null,
    var inventories: List<Inventory>? = null,
    var location_id: Int? = null,
    var modified_on: String? = null,
    var name: String? = null,
    var opt1: String? = "",
    var opt2: String? = "",
    var opt3: String? = "",
    var input_vat_id: Int? = null,
    var input_vat_rate: Int? = 0,
    var output_vat_id: Int? = null,
    var output_vat_rate: Int? = 0,
    var packsize: Boolean? = null,
    var packsize_quantity: Int? = 0,
    var packsize_root_id: Int? = null,
    var packsize_root_name: Any? = null,
    var packsize_root_sku: Any? = null,
    var product_id: Int? = null,
    var product_name: String? = null,
    var product_status: Any? = null,
    var product_type: String? = null,
    var sellable: Boolean? = null,
    var sku: String? = null,
    var status: String? = null,
    var tax_included: Boolean? = null,
    var taxable: Boolean? = null,
    var tenant_id: Int? = null,
    var unit: String? = "",
    var variant_prices: List<VariantPriceData>? = null,
    var variant_retail_price: Double? = 0.0,
    var variant_whole_price: Double? = 0.0,
    var variant_import_price: Double? = 0.0,
    var warranty: Boolean? = null,
    var warranty_term_id: Any? = null,
    var weight_unit: String? = "",
    var weight_value: Double? = 0.0,
) {
    companion object {
        fun getVariant(
            apiVariant: List<com.example.snphmsapo.api.model.datalistproduct.VariantData>
        ): List<Variant> {
            return apiVariant.map { variant ->
                Variant(
                    id = variant.id,
                    product_id = variant.product_id,
                    // thay bằng addAll nếu null thì add list rỗng
                    image_id = variant.image_id,
                    images = variant.images?.let { ImageX.getImageX(it) },
                    name = variant.name,
                    sellable = variant.sellable,
                    sku = variant.sku,
                    barcode = variant.barcode,
                    weight_value = variant.weight_value,
                    weight_unit = variant.weight_unit,
                    unit = variant.unit,
                    opt1 = variant.opt1,
                    tax_included = variant.tax_included,
                    taxable = variant.taxable,
                    input_vat_rate = variant.input_vat_rate.toInt(),
                    output_vat_rate = variant.output_vat_rate.toInt(),
                    inventories = Inventory.getInventories(variant.inventories),
                    packsize = variant.packsize,
                    product_name = variant.product_name,
                    packsize_root_id = variant.packsize_root_id,
                    variant_import_price = variant.variant_import_price,
                    variant_retail_price = variant.variant_retail_price,
                    variant_whole_price = variant.variant_whole_price,

                )
            }

        }

        fun getVariantDetail(
            apiVariant: com.example.snphmsapo.api.model.datalistproduct.VariantData
        ): Variant {
            return Variant(
                id = apiVariant.id,
                product_id = apiVariant.product_id,
                images = apiVariant.images?.let { ImageX.getImageX(it) },
                name = apiVariant.name,
                sku = apiVariant.sku,
                sellable = apiVariant.sellable,
                barcode = apiVariant.barcode,
                weight_value = apiVariant.weight_value,
                weight_unit = apiVariant.weight_unit,
                unit = apiVariant.unit,
                opt1 = option(apiVariant.opt1),
                opt2 = option(apiVariant.opt2),
                opt3 = option(apiVariant.opt3),
                tax_included = apiVariant.tax_included,
                taxable = apiVariant.taxable,
                input_vat_rate = apiVariant.input_vat_rate.toInt(),
                output_vat_rate = apiVariant.output_vat_rate.toInt(),
                inventories = Inventory.getInventories(apiVariant.inventories),
                packsize = apiVariant.packsize,
                product_name = apiVariant.product_name,
                packsize_root_id = apiVariant.packsize_root_id,
                variant_import_price = apiVariant.variant_import_price,
                variant_retail_price = apiVariant.variant_retail_price,
                variant_whole_price = apiVariant.variant_whole_price,
            )
        }


        fun totalAvailable(inventories: List<Inventory>): Double {
            return inventories.sumOf { it.available!! }
        }

        fun totalOnHand(inventories: List<Inventory>): Double {
            return inventories.sumOf { it.on_hand!! }
        }

         fun getImageVariant(
            images: List<com.example.snphmsapo.api.model.datalistproduct.ImageXData>?,
            image_id: Int
        ): String {
            val imgVariant = images?.find { it.id == image_id }
            return imgVariant?.full_path ?: ""
        }

         private fun option(op: String?): String {
            if (op != null) {
                return op
            } else {
                return ""
            }
        }


    }
}

