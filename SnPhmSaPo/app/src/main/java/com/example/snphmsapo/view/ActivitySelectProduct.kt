package com.example.snphmsapo.view

import DividerItemDecorations
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snphmsapo.R
import com.example.snphmsapo.contract.ScrollListenerActions
import com.example.snphmsapo.contract.SelectProductContract
import com.example.snphmsapo.databinding.ActivitySelectProductBinding
import com.example.snphmsapo.model.ListVariant
import com.example.snphmsapo.model.OrderLineItem
import com.example.snphmsapo.presenter.SelectProductPresenter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*


class ActivitySelectProduct : AppCompatActivity(), SelectProductContract.ShowData,
    ScrollListenerActions {

    companion object {
        const val KEY_ORDER_LINE_ITEM = "KEY_ORDER_LINE_ITEM"
        const val KEY_MY_PREFS = "KEY_MY_PREFS"
        const val KEY_SWITCH_STATE = "KEY_SWITCH_STATE"
    }

    private lateinit var binding: ActivitySelectProductBinding
    private val presenter: SelectProductPresenter by lazy {
        SelectProductPresenter(this)
    }

    private val customScrollListener = CustomScrollListener(this)
    private val list: MutableList<Any> = mutableListOf()
    private val orderLineItem: MutableList<OrderLineItem> = mutableListOf()
    private val gson = Gson()
    private var adapter: AdapterSelectProduct? = null
    private var currentPage = 1
    private var enableLoadMore = false
    private var loadMoreJob: Job? = null


    //    private var adapter:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        startEdt()
        addRv()
        addOrderLineItem()
        getData()
        click()

    }

    override fun onBackPressed() {
        navigateToOrderActivity()
    }

    override fun showDataVariantProduct(data: ListVariant) {

        if (currentPage > 1) {
            loadMoreJob?.cancel()
            adapter?.setProgressBar()

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
            val query = binding.edtSelectProductSearch.text.toString().trim()
            currentPage += 1
            adapter?.setProgressBar()
            // Hủy bỏ Job hiện tại nếu có
            loadMoreJob?.cancel()
            // Tạo một tác vụ mới cho load more
            loadMoreJob = CoroutineScope(Dispatchers.Main).launch {
                delay(200) // Tạo thời gian trễ 200ms
                // Sau khi thời gian trễ, gọi API
                presenter.callApiVariantProduct(currentPage, query)
            }
        }
    }

    private fun startEdt() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(
            binding.edtSelectProductSearch, InputMethodManager.SHOW_IMPLICIT
        )
        if (inputMethodManager.isActive) {
            binding.edtSelectProductSearch.requestFocus()
        } else {
            binding.edtSelectProductSearch.clearFocus()
        }

        val sharedPreferences = getSharedPreferences(KEY_MY_PREFS, Context.MODE_PRIVATE)
        val switchState = sharedPreferences.getBoolean(KEY_SWITCH_STATE, false)
        binding.swSelectProductCheck.isChecked = switchState
        if (binding.swSelectProductCheck.isChecked) {
            binding.constSelectProductTow.visibility = View.VISIBLE
        } else {
            binding.constSelectProductTow.visibility = View.GONE
        }
    }

    private fun addOrderLineItem() {
        val orderLineItemJson = intent.getStringExtra(KEY_ORDER_LINE_ITEM)
        val type = object : TypeToken<MutableList<OrderLineItem>>() {}.type
        val orderLineItemList = gson.fromJson<MutableList<OrderLineItem>>(orderLineItemJson, type)
        if (orderLineItemList != null) {
            adapter?.addOrderLineItem(orderLineItemList)
        }
        else{
            adapter?.addOrderLineItem(emptyList())
        }
    }

    private fun getData() {
        val query = binding.edtSelectProductSearch.text.toString().trim()
        presenter.callApiVariantProduct(currentPage, query)
    }


    private fun reset() {
        binding.swrlSelectProduct.isRefreshing = true
        currentPage = 1
        adapter?.clear()
    }

    private fun hideRefreshing() {
        binding.swrlSelectProduct.isRefreshing = false
    }

    private fun click() {
        binding.ivbtnSelectProductBack.setOnClickListener {
            navigateToOrderActivity()
        }
        // tìm kiếm
        binding.edtSelectProductSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // trước
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // lập tức

            }

            override fun afterTextChanged(p0: Editable?) {
                // sau
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    delay(500) // Đợi 0.5 giây
                    reset()
                    getData()
                }
            }
        })

        // Khởi tạo SharedPreferences
        val sharedPreferences = getSharedPreferences(KEY_MY_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // chọn nhiều, chọn ít
        binding.swSelectProductCheck.setOnCheckedChangeListener { _, isChecked ->
            // Lưu trạng thái của Switch vào SharedPreferences
            editor.putBoolean(KEY_SWITCH_STATE, isChecked)
            editor.apply()
            if (isChecked) {
                // Hành động khi Switch được bật (on)
                binding.constSelectProductTow.visibility = View.VISIBLE
            } else {
                // Hành động khi Switch được tắt (off)
                binding.constSelectProductTow.visibility = View.GONE

            }
        }

        // tắt refresh
        binding.swrlSelectProduct.setOnRefreshListener {
            hideRefreshing()
        }

        // chọn lại
        binding.btnSelectProductReset.setOnClickListener {
            addOrderLineItem()
        }

        // chọn nhiều
        binding.btnSelectProductConfim.setOnClickListener {
            navigateToOrderActivity()
        }

    }


    private fun addRv() {
        adapter = AdapterSelectProduct(list, orderLineItem)
        binding.rvSelectProduct.adapter = adapter
        binding.rvSelectProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecorations(this)
        binding.rvSelectProduct.addItemDecoration(dividerItemDecoration)
        binding.rvSelectProduct.addOnScrollListener(customScrollListener)
        adapter?.onClickItemVariant = {
            if (!binding.swSelectProductCheck.isChecked) {
                navigateToOrderActivity()
            }
        }

    }

    private fun navigateToOrderActivity() {
        val orderLineItemJson = gson.toJson(orderLineItem)
        val intent = Intent(this, ActivityOrder::class.java)
        intent.putExtra(KEY_ORDER_LINE_ITEM, orderLineItemJson)
        startActivity(intent)
        finish()
    }

}

