package com.example.snphmsapo.model

data class OrderLineItem(
    var barcode: String? = null,
    var composite_item_domains: List<Any>? = null,
    var created_on: String? = null,
    var discount_amount: Int? = 0,
    var discount_items: List<Any>? = null,
    var discount_rate: Int? = 0,
    var discount_reason: Any? = null,
    var discount_value: Int? = 0,
    var distributed_discount_amount: Int? = 0,
    var height_text_term_compo: Int? = 0,
    var id: Int? = 0,
    var is_composite: Boolean? = null,
    var is_freeform: Boolean? = null,
    var is_packsize: Boolean? = null,
    var line_amount: Double? = 0.0,
    var line_promotion_type: Any? = null,
    var lots_dates: Any? = null,
    var lots_number_code1: Any? = null,
    var lots_number_code2: Any? = null,
    var lots_number_code3: Any? = null,
    var lots_number_code4: Any? = null,
    var modified_on: String? = null,
    var note: Any? = null,
    var pack_size_quantity: Any? = null,
    var pack_size_root_id: Any? = null,
    var price: Double = 0.0,
    var product_id: Int = 0,
    var product_name: String? = null,
    var product_type: String? = null,
    var quantity: Double = 0.0,
    var serials: List<Any>? = null,
    var sku: String? = null,
    var tax_amount: Int? = 0,
    var tax_included: Boolean? = null,
    var tax_rate: Int? = 0,
    var tax_rate_override: Int? = 0,
    var tax_type_id: Any? = null,
    var unit: String? = null,
    var variant_id: Int = 0,
    var variant_name: String? = null,
    var variant_options: String? = null,
    var warranty: Any? = null
) {
    companion object {
        fun getOrderLineItem(variant: Variant): OrderLineItem {
            return OrderLineItem(
                product_id = variant.product_id!!,
                variant_id = variant.id!!,
                product_name = variant.product_name,
                variant_name = variant.name,
                tax_included = variant.tax_included,
                price = variant.variant_retail_price!!,
                is_packsize = variant.packsize,
                sku = variant.sku,
                barcode = variant.barcode,
                unit = variant.unit,
                variant_options = "${variant.opt1} / ${variant.opt2} / ${variant.opt3}",
                tax_rate = variant.output_vat_rate,
                quantity = 1.0
            )
        }
    }
}