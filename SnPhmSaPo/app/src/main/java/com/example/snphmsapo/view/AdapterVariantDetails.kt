package com.example.snphmsapo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snphmsapo.R
import com.example.snphmsapo.databinding.ItemVariantDetailBinding
import com.example.snphmsapo.model.Variant
import com.example.snphmsapo.model.Number.formatNumber
import com.squareup.picasso.Picasso

class AdapterVariantDetails(
    var listVariantDetail: MutableList<Variant>
) : RecyclerView.Adapter<AdapterVariantDetails.ItemVerProDetailViewHolder>() {

    var onClickItemVariant: ((idProduct: Int, idVariant: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVerProDetailViewHolder {
        val binding = ItemVariantDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemVerProDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemVerProDetailViewHolder, position: Int) {
        val item = listVariantDetail[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickItemVariant!!.invoke(item.product_id!!, item.id!!)
        }
    }

    override fun getItemCount(): Int {
        return listVariantDetail.size
    }

    inner class ItemVerProDetailViewHolder(var binding: ItemVariantDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Variant) {
            // ảnh
            Picasso.get().load(item.images?.firstOrNull()?.full_path)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(binding.ivVariantDetailImage)

            // thông tin
            binding.txtVariantDetailNameVariant.text = item.name
            binding.txtVariantDetailSKU.text = item.sku
            // giá bán
            binding.txtVariantDetailPrice.text = item.variant_retail_price?.formatNumber()
            // số lượng có thể bán và tồn kho
            binding.txtVariantDetailAvailable.text = item.inventories?.let {
                Variant.totalAvailable(
                    it
                )
            }?.formatNumber()
            binding.txtVariantDetailOnHand.text = item.inventories?.let {
                Variant.totalOnHand(it)
            }?.formatNumber()

            // phiên bản con
            if (item.packsize == false) {
                binding.ivVariantDetailTowVariant.visibility = View.GONE
            }
        }
    }

}

