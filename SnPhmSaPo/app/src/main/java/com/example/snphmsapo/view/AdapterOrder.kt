package com.example.snphmsapo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snphmsapo.R
import com.example.snphmsapo.databinding.ItemOrderBinding
import com.example.snphmsapo.model.Number.formatNumber
import com.example.snphmsapo.model.OrderLineItem

class AdapterOrder(
    private val orderLineItem: MutableList<OrderLineItem>
) : RecyclerView.Adapter<AdapterOrder.OrderViewHolder>() {

    var onClickDelete: ((index: Int) -> Unit)? = null
    var onClickDialog: ((index: Int) -> Unit)? = null
    var onClickChange: (() -> Unit)?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = orderLineItem[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return orderLineItem.size
    }

    inner class OrderViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderLineItem) {
            binding.txtOrderName.text = item.variant_name
            binding.txtOrderSKU.text = binding.txtOrderSKU.context.getString(R.string.SKU, item.sku)
            binding.txtOrderPrice.text = item.price.formatNumber()
            binding.txtOrderQuantity.text = item.quantity.formatNumber()

            // click
            binding.ivQuantityDelete.setOnClickListener {
                onClickDelete!!.invoke(adapterPosition)
            }
            binding.ivOrderReduce.setOnClickListener {
                item.quantity -= 1
                if (item.quantity <= 0) {
                    onClickDelete!!.invoke(adapterPosition)
                } else {
                    binding.txtOrderQuantity.text = item.quantity.formatNumber()
                    onClickChange!!.invoke()
                }
            }
            binding.ivOrderIncrease.setOnClickListener {
                item.quantity += 1
                binding.txtOrderQuantity.text = item.quantity.formatNumber()
                onClickChange!!.invoke()
            }
            binding.txtOrderQuantity.setOnClickListener {
                onClickDialog!!.invoke(adapterPosition)
            }

        }
    }

}