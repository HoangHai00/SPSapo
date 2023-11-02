package com.example.snphmsapo.view

import DividerItemDecorations
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snphmsapo.R
import com.example.snphmsapo.contract.ListProductContract
import com.example.snphmsapo.contract.ScrollListenerActions
import com.example.snphmsapo.presenter.ListProductPresenter
import com.example.snphmsapo.databinding.ActivityListProductBinding
import com.example.snphmsapo.model.ListProduct
import com.example.snphmsapo.model.ListVariant
import kotlinx.coroutines.*


class ActivityListProduct : AppCompatActivity(), ListProductContract.ShowData,
    ScrollListenerActions {

    companion object {
        // truyền dữ liệu intent
        const val KEY_ID_PRODUCT = "KEY_ID_PRODUCT"
        const val KEY_ID_VARIANT = "KEY_ID_VARIANT"
    }

    private lateinit var binding: ActivityListProductBinding
    private val presenter: ListProductPresenter by lazy {
        ListProductPresenter(this)
    }
    private var currentPage = 1
    private var adapter: AdapterListProduct? = null
    private val list: MutableList<Any> = mutableListOf()
    private var isProductVariant = true
    private var enableLoadMore = false
    private var loadMoreJob: Job? = null
    private val customScrollListener = CustomScrollListener(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // khởi tạo viewBinding
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        click()
        addRvProduct()
        getData()
    }


    override fun showDataProduct(data: ListProduct) {
        if (currentPage > 1) {
            loadMoreJob?.cancel()
            adapter?.setProgressBar()

        }
        if (currentPage == 1) {
            binding.txtListProductProductNumber.text = getString(
                R.string.TitleProduct,
                data.metadata!!.total
            )
        }
        hideRefreshing()
        enableLoadMore = false
        adapter!!.add(data.products!!)
        customScrollListener.enableLoading(data.metadata?.nextPage() ?: false)

    }

    override fun showDataVariantProduct(data: ListVariant) {
        if (currentPage > 1) {
            loadMoreJob?.cancel()
            adapter?.setProgressBar()

        }
        if (currentPage == 1) {
            binding.txtListProductProductNumber.text = getString(
                R.string.TitleVariant,
                data.metadata!!.total
            )
        }
        hideRefreshing()
        enableLoadMore = false
        adapter!!.add(data.variants!!)
        customScrollListener.enableLoading(data.metadata?.nextPage() ?: false)
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.internet), Toast.LENGTH_SHORT).show()
    }

    override fun loadNextPageData() {
        if (!enableLoadMore) {
            enableLoadMore = true // Đánh dấu là đang trong quá trình load more
            val query = binding.edtListProductSearch.text.toString().trim()
            currentPage += 1
            adapter?.setProgressBar()
            // Hủy bỏ Job hiện tại nếu có
            loadMoreJob?.cancel()
            loadMoreJob = CoroutineScope(Dispatchers.Main).launch {
                delay(200) // Tạo thời gian trễ 200ms
                // Sau khi thời gian trễ, gọi API
                if (isProductVariant) {
                    presenter.callApiProduct(currentPage, query)
                } else {
                    presenter.callApiVariantProduct(currentPage, query)
                }
            }
        }
    }


    private fun reset() {
        binding.swrlListProduct.isRefreshing = true
        currentPage = 1
        adapter?.clear()
    }

    private fun click() {
        // đổi danh sách chi tiết sản phẩm
        binding.ivbtnListProductList.setOnClickListener {
            // layout
            binding.txtListProductTitle.text = getString(R.string.ListVariant)
            binding.ivbtnListProductList.visibility = View.GONE
            binding.ivbtnListProductVarianProduct.visibility = View.VISIBLE
            // call lại api
            isProductVariant = false
            adapter?.mode = AdapterListProduct.TYPE_VARIANT
            reset()
            getData()
        }
        // đổi danh sách sản phẩm
        binding.ivbtnListProductVarianProduct.setOnClickListener {
            // layout
            binding.ivbtnListProductList.visibility = View.VISIBLE
            binding.ivbtnListProductVarianProduct.visibility = View.GONE
            binding.txtListProductTitle.text = getString(R.string.ListProduct)
            // call lại api
            isProductVariant = true
            adapter?.mode = AdapterListProduct.TYPE_PRODUCT
            reset()
            getData()
        }
        // back
        binding.ivbtnListProductBack.setOnClickListener {
            finish()
        }
        // tìm kiếm
        binding.edtListProductSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // trước
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    delay(500) // Đợi 0.5 giây
                    reset()
                    getData()
                }


            }
        })

    }

    private fun getData() {
        val query = binding.edtListProductSearch.text.toString().trim()
        if (isProductVariant) {
            presenter.callApiProduct(currentPage, query)
            refreshData(isProductVariant)
        } else {
            presenter.callApiVariantProduct(currentPage, query)
            refreshData(isProductVariant)
        }
    }

    private fun hideRefreshing() {
        binding.swrlListProduct.isRefreshing = false
    }

    private fun refreshData(isProduct: Boolean) {
        binding.swrlListProduct.setOnRefreshListener {
            val query = binding.edtListProductSearch.text.toString().trim()
            reset()
            // Gọi API tương ứng
            if (isProduct) {
                presenter.callApiProduct(currentPage, query)
            } else {
                presenter.callApiVariantProduct(currentPage, query)
            }
        }
    }

    private fun addRvProduct() {
        adapter = AdapterListProduct(list)
        binding.rvListProduct.adapter = adapter
        binding.rvListProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecorations(this)
        binding.rvListProduct.addItemDecoration(dividerItemDecoration)
        binding.rvListProduct.addOnScrollListener(customScrollListener)

        // clickItemProduct
        adapter?.onClickItemProduct = { idProduct ->
            val intentProduct = Intent(this, ActivityProductDetails::class.java)
            val bundle = Bundle()
            bundle.putInt(KEY_ID_PRODUCT, idProduct)
            intentProduct.putExtras(bundle)
            startActivity(intentProduct)
        }
        // clickItemVariant
        adapter?.onClickItemVariant = { idProduct, idVariant ->
            val intentVariant = Intent(this, ActivityVarianDetails::class.java)
            val bundle = Bundle()
            bundle.putInt(KEY_ID_PRODUCT, idProduct)
            bundle.putInt(KEY_ID_VARIANT, idVariant)
            intentVariant.putExtras(bundle)
            startActivity(intentVariant)
        }

    }


}



