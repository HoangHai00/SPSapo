package com.example.snphmsapo.view

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snphmsapo.R
import com.example.snphmsapo.databinding.ItemProgressbarBinding
import com.example.snphmsapo.databinding.ItemVariantBinding
import com.example.snphmsapo.model.OrderLineItem
import com.example.snphmsapo.model.Variant
import com.example.snphmsapo.model.Number.formatNumber
import com.squareup.picasso.Picasso

class AdapterSelectProduct(
    private val list: MutableList<Any>,
    private val orderLineItem: MutableList<OrderLineItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_VARIANT = 1
        const val TYPE_PROGRESSBAR = 2
    }

    var onClickItemVariant: (() -> Unit)? = null
    var mode = TYPE_VARIANT
    private var isLoading = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_VARIANT -> {
                val binding = ItemVariantBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                VariantViewHolder(binding)
            }
            TYPE_PROGRESSBAR -> {
                val binding = ItemProgressbarBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProgressBarViewHolder(binding)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VariantViewHolder -> {
                val variant = list[position] as Variant
                holder.bindVariant(variant)
                holder.itemView.setOnClickListener {
                    holder.increaseQuantity(variant)
                    onClickItemVariant?.invoke()
                }
            }
            is ProgressBarViewHolder -> {

            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoading && list.lastIndex == position) {
            return TYPE_PROGRESSBAR
        }
        return mode
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setProgressBar() {
        isLoading = !isLoading
        val lastIndex = this.list.lastIndex
        if (isLoading) {
            list.add(Any()) // Thêm một item giả vào danh sách để hiển thị ProgressBar
            Handler(Looper.getMainLooper()).post {
                notifyItemRangeChanged(lastIndex, 1)
            }
        } else {
            list.removeAt(list.size - 1) // Loại bỏ item giả
            notifyItemRangeRemoved(lastIndex, 1)
        }
    }

    fun clear() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun add(listData: List<Any>) {
        val lastIndex = this.list.lastIndex
        this.list.addAll(listData)
        notifyItemRangeChanged(lastIndex + 1, listData.size)
    }

    fun addOrderLineItem(orderLineItem: List<OrderLineItem>) {
        this.orderLineItem.clear()
        this.orderLineItem.addAll(orderLineItem)
        notifyDataSetChanged()
    }

    inner class VariantViewHolder(private var binding: ItemVariantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val quantity = binding.txtVariantQuantity
        fun bindVariant(itemVariant: Variant) {
            // ảnh
            Picasso.get().load(itemVariant.images?.firstOrNull()?.full_path)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(binding.ivVariantImage)

            // thông tin
            binding.txtVariantNameVariant.text = itemVariant.name
            binding.txtVariantSKU.text = itemVariant.sku
            // giá bán
            binding.txtVariantPrice.text = itemVariant.variant_retail_price?.formatNumber()

            // số lượng có thể bán
            binding.txtVariantAvailable.text = itemVariant.inventories?.let {
                Variant.totalAvailable(
                    it
                )
            }?.formatNumber()

            val existingOrderLineItem = orderLineItem.find { it.variant_id == itemVariant.id }
            if (existingOrderLineItem != null && existingOrderLineItem.quantity > 0) {
                quantity.visibility = View.VISIBLE
                quantity.text = existingOrderLineItem.quantity.formatNumber()
            } else {
                quantity.visibility = View.GONE
            }
        }

        fun increaseQuantity(itemVariant: Variant) {
            val existingOrderLineItem = orderLineItem.find { it.variant_id == itemVariant.id }
            if (existingOrderLineItem != null) {
                existingOrderLineItem.quantity++
                quantity.text = existingOrderLineItem.quantity.formatNumber()
            } else {
                val newOrderLineItem = OrderLineItem.getOrderLineItem(itemVariant)
                orderLineItem.add(newOrderLineItem)
                quantity.text = newOrderLineItem.quantity.formatNumber()
            }
            quantity.visibility = View.VISIBLE
        }
    }

    inner class ProgressBarViewHolder(private val binding: ItemProgressbarBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}