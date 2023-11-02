package com.example.snphmsapo.model

import android.content.Context
import com.example.snphmsapo.R

data class Order(
    var account_id: Int? = null,
    var allow_no_refund_order_exchange_amount: Boolean? = null,
    var assignee_id: Int? = null,
    var billing_address: Any? = null,
    var business_version: Int? = null,
    var cancelled_on: Any? = null,
    var channel: Any? = null,
    var code: String? = null,
    var completed_on: Any? = null,
    var contact_id: Any? = null,
    var create_invoice: Boolean? = null,
    var created_on: String? = null,
    var customer_data: Any? = null,
    var customer_id: Int? = null,
    var delivery_fee: Any? = null,
    var discount_items: List<Any>? = null,
    var discount_reason: Any? = null,
    var einvoice_status: String? = null,
    var email: Any? = null,
    var expected_delivery_provider_id: Any? = null,
    var expected_delivery_type: Any? = null,
    var expected_payment_method_id: Any? = null,
    var finalized_on: Any? = null,
    var finished_on: Any? = null,
    var from_order_return_id: Any? = null,
    var fulfillment_status: String? = null,
    var fulfillments: List<Any>? = null,
    var id: Int? = null,
    var interconnection_status: Any? = null,
    var issued_on: String? = null,
    var location_id: Int? = null,
    var modified_on: String? = null,
    var note: Any? = null,
    var order_coupon_code: Any? = null,
    var order_discount_amount: Int? = null,
    var order_discount_rate: Int? = null,
    var order_discount_varue: Int? = null,
    var order_line_items: MutableList<OrderLineItem> = mutableListOf(),
    var order_return_exchange: Any? = null,
    var order_returns: List<Any>? = null,
    var packed_status: String? = null,
    var payment_status: String? = null,
    var phone_number: Any? = null,
    var prepayments: List<Any>? = null,
    var price_list_id: Int? = null,
    var print_status: Boolean? = null,
    var process_status_id: Any? = null,
    var promotion_redemptions: List<Any>? = null,
    var reason_cancel_id: Any? = null,
    var received_status: String? = null,
    var reference_number: Any? = null,
    var reference_url: Any? = null,
    var return_status: String? = null,
    var ship_on: Any? = null,
    var ship_on_max: Any? = null,
    var ship_on_min: Any? = null,
    var shipping_address: Any? = null,
    var source_id: Int = 0,
    var status: String = "",
    var tags: List<Any>? = null,
    var tax_treatment: String? = null,
    var tenant_id: Int? = null,
    var total: Int? = null,
    var total_discount: Int? = null,
    var total_order_exchange_amount: Any? = null,
    var total_tax: Int? = null
){
    companion object{
        fun getOrder(context: Context, order_line_items: MutableList<OrderLineItem>):Order{
            return Order(
                source_id = 6482870,
                status = context.getString(R.string.draft),
                order_line_items = order_line_items
            )
        }

        fun getTotalQuantity(order_line_items: MutableList<OrderLineItem>):Double{
            return order_line_items.sumOf { it.quantity }
        }

        fun getTotalMoney(order_line_items: MutableList<OrderLineItem>):Double{
            return order_line_items.sumOf { it.price * it.quantity }
        }
        fun getTotalVat(order_line_items: MutableList<OrderLineItem>): Double {
            return order_line_items.fold(0.0) { accumulator, item ->
                if (item.tax_rate != null) {
                    accumulator + (item.price * item.quantity * item.tax_rate!!.toDouble()) / 100
                } else {
                    accumulator
                }
            }
        }
        fun getTotalProvisional(order_line_items: MutableList<OrderLineItem>):Double{
            return getTotalMoney(order_line_items) + getTotalVat(order_line_items)
        }


    }
}