package com.example.snphmsapo.api.model.order

import com.example.snphmsapo.model.Order

data class OrderLineItemData(
    val barcode: String? = null,
    val composite_item_domains: List<Any>? = null,
    val created_on: String? = null,
    val discount_amount: Int? = null,
    val discount_items: List<Any>? = null,
    val discount_rate: Int? = null,
    val discount_reason: Any? = null,
    val discount_value: Int? = null,
    val distributed_discount_amount: Int? = null,
    val height_text_term_compo: Int? = null,
    val id: Int? = null,
    val is_composite: Boolean? = null,
    val is_freeform: Boolean? = null,
    val is_packsize: Boolean? = null,
    val line_amount: Double? = null,
    val line_promotion_type: Any? = null,
    val lots_dates: Any? = null,
    val lots_number_code1: Any? = null,
    val lots_number_code2: Any? = null,
    val lots_number_code3: Any? = null,
    val modified_on: String? = null,
    val note: Any? = null,
    val pack_size_quantity: Any? = null,
    val pack_size_root_id: Any? = null,
    val price: Double? = null,
    val product_id: Int? = null,
    val product_name: String? = null,
    val product_type: String? = null,
    val quantity: Double? = null,
    val serials: List<Any>? = null,
    val sku: String? = null,
    val tax_amount: Int? = null,
    val tax_included: Boolean? = null,
    val tax_rate: Int? = null,
    val tax_rate_override: Int? = null,
    val tax_type_id: Any? = null,
    val unit: String? = null,
    val variant_id: Int? = null,
    val variant_name: String? = null,
    val variant_options: String? = null,
    val warranty: Any?= null
){
    companion object{
        fun getOrderLineItem(order: Order):List<OrderLineItemData>{
            return order.order_line_items.map { item ->
                OrderLineItemData(  product_id = item.product_id,
                    variant_id = item.variant_id,
                    product_name = item.product_name!!,
                    variant_name = item.variant_name!!,
                    tax_included = item.tax_included!!,
                    price = item.price,
                    is_packsize = item.is_packsize!!,
                    sku = item.sku!!,
                    barcode = item.barcode!!,
                    unit = item.unit,
                    variant_options = item.variant_options!!,
                    tax_rate = item.tax_rate!!,
                    quantity = item.quantity)
            }
        }
    }
}