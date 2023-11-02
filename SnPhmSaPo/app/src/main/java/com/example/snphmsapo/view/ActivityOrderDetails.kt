package com.example.snphmsapo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snphmsapo.databinding.ActivityOrderDetailsBinding
import com.example.snphmsapo.model.Inventory
import com.example.snphmsapo.model.OrderLineItem
import com.example.snphmsapo.model.Variant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ActivityOrderDetails : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailsBinding
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
    }

    private fun getData() {
        val orderLineItemJson = intent.getStringExtra(ActivityOrder.KEY_ITEM_ORDER)
        val item = gson.fromJson(orderLineItemJson, OrderLineItem::class.java)

    }
}