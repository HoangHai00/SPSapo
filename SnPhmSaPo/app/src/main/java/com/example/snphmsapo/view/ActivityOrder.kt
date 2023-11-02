package com.example.snphmsapo.view

import DividerItemDecorations
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snphmsapo.R
import com.example.snphmsapo.contract.OrderContract
import com.example.snphmsapo.databinding.ActivityOrderBinding
import com.example.snphmsapo.model.Number.formatNumber
import com.example.snphmsapo.model.Order
import com.example.snphmsapo.model.OrderLineItem
import com.example.snphmsapo.presenter.OrderPresenter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ActivityOrder : AppCompatActivity(), OrderContract.ShowData {

    companion object {
        const val KEY_ITEM_ORDER = "KEY_ITEM_ORDER"
    }

    private lateinit var binding: ActivityOrderBinding
    private val presenter: OrderPresenter by lazy {
        OrderPresenter(this)
    }
    private val orderLineItem: MutableList<OrderLineItem> = mutableListOf()
    private val gson = Gson()
    private var adapter: AdapterOrder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        addRv()
        click()
        upTotal()
    }

    override fun onBackPressed() {
        if (orderLineItem.isEmpty()) {
            finish()
        } else {
            cancelOrder()
        }
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.internet), Toast.LENGTH_SHORT).show()
    }

    override fun success() {
        Toast.makeText(this, getString(R.string.PostSuccess), Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).post {
            finish()
        }
    }

    private fun addRv() {
        if (orderLineItem.isEmpty()) {
            binding.constOrderSelectProduct.visibility = View.VISIBLE
            binding.rvOrder.visibility = View.GONE
            binding.btnOrderPost.setBackgroundResource(R.drawable.shape_cri_grey_corner)
        } else {
            binding.constOrderSelectProduct.visibility = View.GONE
            binding.rvOrder.visibility = View.VISIBLE
            binding.btnOrderPost.setBackgroundResource(R.drawable.shape_cri_blue_corner)
            adapter = AdapterOrder(orderLineItem)
            binding.rvOrder.adapter = adapter
            binding.rvOrder.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration = DividerItemDecorations(this)
            binding.rvOrder.addItemDecoration(dividerItemDecoration)
            adapter?.onClickDelete = {
                showDialogDelete(it)
            }
            adapter?.onClickDialog = {
                showDialogQuantity(it)
            }
            adapter?.onClickChange = {
                upTotal()
            }
        }
    }

    private fun showDialogDelete(index: Int) {
        val dialog = DialogDelete(this, onNumberEntered = {
            orderLineItem.removeAt(index)
            adapter?.notifyItemRemoved(index)
            upTotal()
            if (orderLineItem.isEmpty()){
                binding.constOrderSelectProduct.visibility = View.VISIBLE
                binding.rvOrder.visibility = View.GONE
                binding.btnOrderPost.setBackgroundResource(R.drawable.shape_cri_grey_corner)
            }
        }, orderLineItem[index].variant_name!!)
        dialog.show()
    }

    private fun showDialogQuantity(index: Int) {
        val quantity = orderLineItem[index].quantity
        val dialog = DialogQuantity(this, onNumberEntered = {
            orderLineItem[index].quantity = it
            adapter?.notifyItemChanged(index)
            upTotal()
        }, quantity)
        dialog.show()
    }

    private fun getData() {
        // Lấy chuỗi JSON từ Intent
        val orderLineItemJson = intent.getStringExtra(ActivitySelectProduct.KEY_ORDER_LINE_ITEM)

        // Giải mã chuỗi JSON thành danh sách orderLineItem
        val type = object : TypeToken<MutableList<OrderLineItem>>() {}.type
        val orderLineItemList = gson.fromJson<MutableList<OrderLineItem>>(orderLineItemJson, type)
        if (orderLineItemList != null) {
            orderLineItem.clear()
            orderLineItem.addAll(orderLineItemList)
        }
    }

    private fun click() {
        // chuyển activity chọn
        val nextListProduct: View.OnClickListener?
        nextListProduct = View.OnClickListener {
            if (orderLineItem.isEmpty()) {
                val intent = Intent(this, ActivitySelectProduct::class.java)
                startActivity(intent)
            } else {
                val orderLineItemJson = gson.toJson(orderLineItem)
                // Đóng gói chuỗi JSON vào Intent
                val intent = Intent(this, ActivitySelectProduct::class.java)
                intent.putExtra(ActivitySelectProduct.KEY_ORDER_LINE_ITEM, orderLineItemJson)
                startActivity(intent)
            }
            finish()
        }
        binding.constOrderSelectProduct.setOnClickListener(nextListProduct)
        binding.llOrderSearch.setOnClickListener(nextListProduct)

        // tạo đơn hàng
        binding.btnOrderPost.setOnClickListener {
            if (orderLineItem.isNotEmpty()){
                val order = Order.getOrder(this, orderLineItem)
                presenter.postOrder(order)
            }
        }
        // hủy đơn
        binding.ivbtnOrderBack.setOnClickListener {
            if (orderLineItem.isEmpty()) {
                finish()
            } else {
                cancelOrder()
            }
        }
    }

    private fun upTotal() {
        binding.txtOrderTotalQuantity.text = Order.getTotalQuantity(orderLineItem).formatNumber()
        binding.txtOrderTotalMoney.text = Order.getTotalMoney(orderLineItem).formatNumber()
        val totalVat = Order.getTotalVat(orderLineItem)
        if (totalVat > 0) {
            binding.txtOrderTotalVat.text = totalVat.formatNumber()
            binding.llOrderVat.visibility = View.VISIBLE
        } else {
            binding.llOrderVat.visibility = View.GONE
        }

        binding.txtOrderTotalProvisional.text =
            Order.getTotalProvisional(orderLineItem).formatNumber()

    }

    private fun cancelOrder() {
        val dialog = DialogCancelOrder(this, onNumberEntered = {
            finish()
        })
        dialog.show()
    }


}
