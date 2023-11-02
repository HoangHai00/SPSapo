package com.example.snphmsapo.view

import DividerItemDecorations
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snphmsapo.R
import com.example.snphmsapo.contract.VariantProductDetailContract
import com.example.snphmsapo.presenter.VariantDetailPresenter
import com.example.snphmsapo.databinding.ActivityVariantDetailsBinding
import com.example.snphmsapo.model.Variant
import com.example.snphmsapo.model.VariantDetail
import com.example.snphmsapo.model.Number.formatNumber
import com.squareup.picasso.Picasso


class ActivityVarianDetails : AppCompatActivity(), VariantProductDetailContract.ShowData {

    private lateinit var binding: ActivityVariantDetailsBinding
    private val presenter: VariantDetailPresenter by lazy {
        VariantDetailPresenter(this)
    }

    private var dsVariant = mutableListOf<Variant>()
    private lateinit var adapterListVariant: AdapterVariantDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVariantDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val receivedIntent = intent
        if (receivedIntent != null) {
            val bundle = receivedIntent.extras
            if (bundle != null) {
                val idProduct = bundle.getInt(ActivityListProduct.KEY_ID_PRODUCT)
                val idVariant = bundle.getInt(ActivityListProduct.KEY_ID_VARIANT)
                presenter.callApiVariant(idProduct, idVariant)
            }
        }

        clickBtn()
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.internet), Toast.LENGTH_SHORT).show()
    }

    override fun showDataVariant(data: VariantDetail) {
        // ảnh
        if (data.variant.images == null) {
            binding.ivVarianDetailsProduct.setImageResource(R.drawable.ic_image)
            binding.ivVarianDetailsProduct.layoutParams.width = 100
            binding.ivVarianDetailsProduct.layoutParams.height = 100
            binding.ivVarianDetailsProduct.scaleType = ImageView.ScaleType.FIT_XY
        } else {
            Picasso.get().load(data.variant.images?.firstOrNull()?.full_path)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(binding.ivVarianDetailsProduct)
        }

        // thông tin
        binding.txtVarianDetailsNameProduct.text = data.variant.name
        binding.txtVarianDetailsSKU.text = data.variant.sku
        binding.txtVarianDetailsBarcode.text = data.variant.barcode
        binding.txtVarianDetailsWeight.text = getString(
            R.string.Weight,
            data.variant.weight_value?.formatNumber(),
            data.variant.weight_unit
        )
        binding.txtVarianDetailsUnit.text = data.variant.unit

        // giá sản phẩm
        if (data.variant.taxable == true) {
            binding.txtVarianDetailsTitleVat.text = getString(R.string.TaxableTrue)
        } else {
            binding.txtVarianDetailsTitleVat.text = getString(R.string.TaxableFalse)
        }

        if (data.variant.tax_included == true) {
            binding.txtVarianDetailsInputVat.text =
                getString(R.string.Vat, data.variant.input_vat_rate)
            binding.txtVarianDetailsOutputVat.text =
                getString(R.string.Vat, data.variant.output_vat_rate)
        } else {
            binding.constVarianDetailsVat.visibility = View.GONE
        }
        binding.txtVarianDetailsRetailPrice.text = data.variant.variant_retail_price?.formatNumber()

        binding.txtVarianDetailsWholesalePrice.text = data.variant.variant_whole_price?.formatNumber()

        binding.txtVarianDetailsImportPrice.text = data.variant.variant_import_price?.formatNumber()


        // kho hàng
        binding.txtVarianDetailsOnHand.text = data.variant.inventories?.let { Variant.totalOnHand(it).formatNumber() }

        binding.txtVarianDetailsAvailable.text = data.variant.inventories?.let {
            Variant.totalAvailable(
                it
            ).formatNumber()
        }

        // phiên bản to và phiên bản con
        if (data.variant.packsize == true) {
            binding.txtVarianDetailsTitleSize.text = getString(R.string.SizeTow)
            binding.txtVarianDetailsSize.text = data.variant.opt1
            binding.constVarianDetailsVarianProduct.visibility = View.GONE
        } else {
            presenter.callApiProduct(data.variant.product_id!!)
            binding.txtVarianDetailsTitleSize.text = getString(R.string.SizeOne)
            binding.txtVarianDetailsSize.text = data.variant.opt1
        }
    }

    override fun showDataProduct(data: com.example.snphmsapo.model.ProductDetail) {
        adapterListVariant =
            AdapterVariantDetails(dsVariant)
        binding.rvVarianDetailsVerProduct.adapter = adapterListVariant
        binding.rvVarianDetailsVerProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecorations(this)
        binding.rvVarianDetailsVerProduct.addItemDecoration(dividerItemDecoration)

        adapterListVariant.onClickItemVariant = { idProduct, idVariant ->
            val intentVariant = Intent(this, ActivityVarianDetails::class.java)
            val bundle = Bundle()
            bundle.putInt(ActivityListProduct.KEY_ID_PRODUCT, idProduct)
            bundle.putInt(ActivityListProduct.KEY_ID_VARIANT, idVariant)
            intentVariant.putExtras(bundle)
            startActivity(intentVariant)
        }


        val bundle = intent.extras
        if (bundle != null) {
            val idVariant = bundle.getInt(ActivityListProduct.KEY_ID_VARIANT)
            for (variant in data.product.variants as MutableList<Variant>) {
                if (variant.packsize == true && variant.packsize_root_id == idVariant) {
                    dsVariant.addAll(listOf(variant))
                }
            }
            adapterListVariant.notifyDataSetChanged()
        }

    }

    private fun clickBtn() {
        binding.ivbtnVarianDetailsBack.setOnClickListener {
            finish()
        }
    }


}
