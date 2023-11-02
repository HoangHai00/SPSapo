package com.example.snphmsapo.view

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snphmsapo.R
import com.example.snphmsapo.databinding.ItemProductBinding
import com.example.snphmsapo.databinding.ItemProgressbarBinding
import com.example.snphmsapo.databinding.ItemVariantBinding
import com.example.snphmsapo.model.Product
import com.example.snphmsapo.model.Variant
import com.example.snphmsapo.model.Number.formatNumber
import com.squareup.picasso.Picasso


class AdapterListProduct(
    private val list: MutableList<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_PRODUCT = 0
        const val TYPE_VARIANT = 1
        const val TYPE_PROGRESSBAR = 2
    }

    var onClickItemProduct: ((idProduct: Int) -> Unit)? = null
    var onClickItemVariant: ((idProduct: Int, idVariant: Int) -> Unit)? = null
    var mode = TYPE_PRODUCT
    private var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PRODUCT -> {
                val binding =
                    ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProductViewHolder(binding)
            }
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
            is ProductViewHolder -> {
                val product = list[position] as Product
                holder.bindProduct(product)
                holder.itemView.setOnClickListener {
                    onClickItemProduct!!.invoke(product.id!!)
                }

            }
            is VariantViewHolder -> {
                val variant = list[position] as Variant
                holder.bindVariant(variant)
                holder.itemView.setOnClickListener {
                    onClickItemVariant!!.invoke(variant.product_id!!, variant.id!!)
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
            notifyItemRangeRemoved(lastIndex,1)
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


    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindProduct(itemProduct: Product) {
//
            Picasso.get().load(itemProduct.images?.firstOrNull()?.full_path)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(binding.ivProductImage)
            // thông tin
            binding.txtProductNameProduct.text = itemProduct.name
            binding.txtProductVersionNumber.text = itemProduct.variants?.size.toString()
            binding.txtProductSellNumber.text = itemProduct.variants?.get(0)?.let {
                it.inventories?.let { it1 ->
                    Variant.totalAvailable(
                        it1
                    )
                }?.formatNumber()
            }

        }
    }

    inner class VariantViewHolder(private var binding: ItemVariantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindVariant(itemVariant: Variant) {
            // ảnh

            Picasso.get().load(itemVariant.images?.firstOrNull()?.full_path)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(binding.ivVariantImage)

            binding.txtVariantQuantity.visibility = View.GONE

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

        }
    }

    inner class ProgressBarViewHolder(private val binding: ItemProgressbarBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


}
