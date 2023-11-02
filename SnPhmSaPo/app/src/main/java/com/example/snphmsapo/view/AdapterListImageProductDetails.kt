package com.example.snphmsapo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snphmsapo.databinding.ItemImageProductBinding
import com.example.snphmsapo.model.Image
import com.squareup.picasso.Picasso

class AdapterListImageProductDetails(
    var dsImg: MutableList<Image>
) : RecyclerView.Adapter<AdapterListImageProductDetails.ItemImgProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemImgProductViewHolder {
        val binding =
            ItemImageProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemImgProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemImgProductViewHolder, position: Int) {
        val item = dsImg[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dsImg.size
    }

    inner class ItemImgProductViewHolder(var binding: ItemImageProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            Picasso.get().load(item.full_path).into(binding.ivImageProductImage)
        }
    }

}