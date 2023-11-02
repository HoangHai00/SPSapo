package com.example.snphmsapo.api.model.order

import com.example.snphmsapo.R
import com.example.snphmsapo.model.Order

data class OrderData(
    val account_id: Int? = null,
    val allow_no_refund_order_exchange_amount: Boolean? = null,
    val assignee_id: Int? = null,
    val billing_address: Any? = null,
    val business_version: Int? = null,
    val cancelled_on: Any? = null,
    val channel: Any? = null,
    val code: String? = null,
    val completed_on: Any? = null,
    val contact_id: Any? = null,
    val create_invoice: Boolean? = null,
    val created_on: String? = null,
    val customer_data: Any? = null,
    val customer_id: Int? = null,
    val delivery_fee: Any? = null,
    val discount_items: List<Any>? = null,
    val discount_reason: Any? = null,
    val einvoice_status: String? = null,
    val email: Any? = null,
    val expected_delivery_provider_id: Any? = null,
    val expected_delivery_type: Any? = null,
    val expected_payment_method_id: Any? = null,
    val finalized_on: Any? = null,
    val finished_on: Any? = null,
    val from_order_return_id: Any? = null,
    val fulfillment_status: String? = null,
    val fulfillments: List<Any>? = null,
    val id: Int? = null,
    val interconnection_status: Any? = null,
    val issued_on: String? = null,
    val location_id: Int? = null,
    val modified_on: String? = null,
    val note: Any? = null,
    val order_coupon_code: Any? = null,
    val order_discount_amount: Int? = null,
    val order_discount_rate: Int? = null,
    val order_discount_value: Int? = null,
    val order_line_items: List<OrderLineItemData>? = null,
    val order_return_exchange: Any? = null,
    val order_returns: List<Any>? = null,
    val packed_status: String? = null,
    val payment_status: String? = null,
    val phone_number: Any? = null,
    val prepayments: List<Any>? = null,
    val price_list_id: Int? = null,
    val print_status: Boolean? = null,
    val process_status_id: Any? = null,
    val promotion_redemptions: List<Any>? = null,
    val reason_cancel_id: Any? = null,
    val received_status: String? = null,
    val reference_number: Any? = null,
    val reference_url: Any? = null,
    val return_status: String? = null,
    val ship_on: Any? = null,
    val ship_on_max: Any? = null,
    val ship_on_min: Any? = null,
    val shipping_address: Any? = null,
    val source_id: Int? = null,
    val status: String? = null,
    val tags: List<Any>? = null,
    val tax_treatment: String? = null,
    val tenant_id: Int? = null,
    val total: Int? = null,
    val total_discount: Int? = null,
    val total_order_exchange_amount: Any? = null,
    val total_tax: Int?= null
){
    companion object{
        fun getOrder(order: Order):OrderData{
            return OrderData(
                source_id = order.source_id,
                status = order.status,
                order_line_items = OrderLineItemData.getOrderLineItem(order)
            )
        }
    }
}