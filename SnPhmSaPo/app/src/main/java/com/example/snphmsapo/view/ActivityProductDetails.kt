package com.example.snphmsapo.view

import DividerItemDecorations
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snphmsapo.contract.ProductDetailContract
import com.example.snphmsapo.presenter.ProductDetailPresenter
import com.example.snphmsapo.R
import com.example.snphmsapo.databinding.ActivityProductDetailsBinding
import com.example.snphmsapo.model.Image
import com.example.snphmsapo.model.ProductDetail
import com.example.snphmsapo.model.Variant
import com.example.snphmsapo.model.Number.formatNumber


class ActivityProductDetails : AppCompatActivity(), ProductDetailContract.ShowData {

    private lateinit var binding: ActivityProductDetailsBinding
    private val presenter: ProductDetailPresenter by lazy {
        ProductDetailPresenter(this)
    }
    private var check = false
    private lateinit var adapterListVariant: AdapterProductDetails
    private var listVarianProduct =
        mutableListOf<Variant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundel()
        clickBtn()


    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.internet), Toast.LENGTH_SHORT).show()
    }

    override fun showData(productDetail: ProductDetail) {
        // ảnh
        binding.rvProductDetailsImgProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        if (productDetail.product.images?.isEmpty() == true) {
            binding.rvProductDetailsImgProduct.visibility = View.GONE
        } else {
            val adapterImgProduct =
                AdapterListImageProductDetails(productDetail.product.images as MutableList<Image>)
            binding.rvProductDetailsImgProduct.adapter = adapterImgProduct
        }

        // sản phẩm 1 phiên bản hoặc nhiều phiên bản
        if (productDetail.product.variants!![0].name ==
            productDetail.product.variants!![0].product_name && productDetail.product.variants?.size == 1
        ) {
            // sản phẩm 1 phiên bản
            showVersionOne(productDetail)
        } else {
            // sản phẩm nhiều phiên bản
            showVersionTow(productDetail)
        }

        // thông tin thêm
        if (productDetail.product.category != null) {
            binding.txtProductDetailsCategory.text = productDetail.product.category
        }
        if (productDetail.product.brand != null) {
            binding.txtProductDetailsBrand.text = productDetail.product.brand
        }
        if (productDetail.product.description != null) {
            binding.txtProductDetailsdescription.text = productDetail.product.description
        }

        // trạng thái giao dịch
        if (productDetail.product.status != getString(R.string.Active)) {
            binding.txtProductDetailsTrading.text = getString(R.string.UnActive)
            binding.txtProductDetailsIconTrading.setBackgroundResource(R.drawable.custom_trading_off)
        }

    }


    private fun clickBtn() {
        binding.ivbtnProductDetailsBack.setOnClickListener {
            finish()
        }
        binding.ivProductDetailsUp.setOnClickListener {
            check = !check
            apply(check)
        }
    }


    private fun getBundel() {
        val receivedIntent = intent
        if (receivedIntent != null) {
            val bundle = receivedIntent.extras
            if (bundle != null) {
                val idProduct = bundle.getInt(ActivityListProduct.KEY_ID_PRODUCT)
                getData(idProduct)
            }
        }
    }

    private fun apply(check: Boolean) {
        if (check == true) {
            binding.constProductDetailsOneInformation.visibility = View.GONE
            binding.constraintSellable.visibility = View.GONE
            binding.ivProductDetailsUp.setImageResource(R.drawable.ic_down)
        } else {
            binding.constProductDetailsOneInformation.visibility = View.VISIBLE
            binding.constraintSellable.visibility = View.VISIBLE
            binding.ivProductDetailsUp.setImageResource(R.drawable.ic_up)
        }

    }

    private fun getData(idProduct: Int) {
        presenter.callApi(idProduct)
    }


    // sản phẩm 1 phiên bản
    private fun showVersionOne(productDetail: ProductDetail) {
        binding.constProductDetailsVersionProduct.visibility = View.GONE
        // thông tin
        binding.txtProductDetailsNameProduct.text = productDetail.product.name
        binding.txtProductDetailsSKU.text = productDetail.product.variants!![0].sku
        binding.txtProductDetailsBarcode.text = productDetail.product.variants!![0].barcode
        binding.txtProductDetailsWeight.text = getString(
            R.string.Weight,
            productDetail.product.variants!![0].weight_value?.formatNumber(),
            productDetail.product.variants!![0].weight_unit
        )

        if (productDetail.product.variants!![0].unit != null) {
            binding.txtProductDetailsUnit.text = productDetail.product.variants!![0].unit
        }
        // giá sản phẩm
        if (productDetail.product.variants!![0].taxable == true) {
            binding.txtProductDetailsTitleVat.text = getString(R.string.TaxableTrue)
        } else {
            binding.txtProductDetailsTitleVat.text = getString(R.string.TaxableFalse)
        }

        if (productDetail.product.variants!![0].tax_included == false) {
            binding.constProductDetailsVat.visibility = View.GONE
        } else {
            binding.txtProductDetailsInputVat.text =
                getString(R.string.Vat, productDetail.product.variants!![0].input_vat_rate)
            binding.txtProductDetailsOutputVat.text =
                getString(R.string.Vat, productDetail.product.variants!![0].output_vat_rate)
        }

        binding.txtProductDetailsRetailPrice.text =
            productDetail.product.variants!![0].variant_retail_price?.formatNumber()
        binding.txtProductDetailsWholesalePrice.text =
            productDetail.product.variants!![0].variant_whole_price?.formatNumber()
        binding.txtProductDetailsImportPrice.text =
            productDetail.product.variants!![0].variant_import_price?.formatNumber()

        // kho hàng
        binding.txtProductDetailsOnHand.text =
            productDetail.product.variants!![0].inventories?.let {
                Variant.totalOnHand(it).formatNumber()
            }

        binding.txtProductDetailsAvailable.text =
            productDetail.product.variants!![0].inventories?.let {
                Variant.totalAvailable(
                    it
                ).formatNumber()
            }

        if (productDetail.product.variants!![0].sellable == true) {
            binding.txtProductDetailsSellable.text = getString(R.string.Available)
            binding.ivProductDetailsSellable.setImageResource(R.drawable.ic_online)
        } else {
            binding.txtProductDetailsSellable.text = getString(R.string.UnAvailable)
            binding.ivProductDetailsSellable.setImageResource(R.drawable.ic_cancel)
        }

    }

    // sản phẩm nhiều phiên bản
    private fun showVersionTow(productDetail: ProductDetail) {
        binding.constProductDetailsDetail.visibility = View.GONE
        binding.constProductDetailsPrice.visibility = View.GONE
        binding.constProductDetailsWarehouse.visibility = View.GONE
        // thông tin
        binding.txtProductDetailsNameProduct.text = productDetail.product.name
        binding.constraintSellable.visibility = View.GONE
        // phiên bản sản phẩm
        addRv(productDetail)
        binding.llProductDetailsMota.setBackgroundResource(R.drawable.custom_rv_pro_detail)

    }


    private fun addRv(productDetail: ProductDetail) {
        listVarianProduct.addAll(productDetail.product.variants as MutableList<Variant>)
        adapterListVariant =
            AdapterProductDetails(listVarianProduct)
        binding.rvProductDetailsVerProduct.adapter = adapterListVariant
        binding.rvProductDetailsVerProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecorations(this)
        binding.rvProductDetailsVerProduct.addItemDecoration(dividerItemDecoration)

        adapterListVariant.onClickItemVariant = { idProduct, idVariant ->
            val intentVariant = Intent(this, ActivityVarianDetails::class.java)
            val bundle = Bundle()
            bundle.putInt(ActivityListProduct.KEY_ID_PRODUCT, idProduct)
            bundle.putInt(ActivityListProduct.KEY_ID_VARIANT, idVariant)
            intentVariant.putExtras(bundle)
            startActivity(intentVariant)
        }
    }


}

