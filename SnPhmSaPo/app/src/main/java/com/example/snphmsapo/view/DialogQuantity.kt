package com.example.snphmsapo.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.snphmsapo.databinding.DialogQuantityBinding
import com.example.snphmsapo.model.Number.formatNumber

class DialogQuantity(context: Context, private val onNumberEntered: (Double) -> Unit,private val quantity:Double) :
    Dialog(context) {
    private lateinit var binding: DialogQuantityBinding
    private var firstClick = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogQuantityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtQuantity.text = quantity.formatNumber()
        click()

    }

    private fun click() {
        binding.btnQuantity0.setOnClickListener { appendNumber(0) }
        binding.btnQuantity1.setOnClickListener { appendNumber(1) }
        binding.btnQuantity2.setOnClickListener { appendNumber(2) }
        binding.btnQuantity3.setOnClickListener { appendNumber(3) }
        binding.btnQuantity4.setOnClickListener { appendNumber(4) }
        binding.btnQuantity5.setOnClickListener { appendNumber(5) }
        binding.btnQuantity6.setOnClickListener { appendNumber(6) }
        binding.btnQuantity7.setOnClickListener { appendNumber(7) }
        binding.btnQuantity8.setOnClickListener { appendNumber(8) }
        binding.btnQuantity9.setOnClickListener { appendNumber(9) }
        binding.btnQuantity.setOnClickListener { appendDots() }
        binding.ivQuantityDelete.setOnClickListener { deleteText() }
        binding.ivQuantityReset.setOnClickListener { resetText() }

        // ấn hủy
        binding.txtQuantityCancel.setOnClickListener { dismiss() }

        // ấn xác nhận
        binding.txtDeleteConfirm.setOnClickListener {
            val enteredNumber = binding.txtQuantity.text.toString().replace(",", "").toDouble()
            onNumberEntered(enteredNumber)
            dismiss()
        }


    }

    private fun appendNumber(number: Int) {
        val currentText = binding.txtQuantity.text.toString()

        if (firstClick) {
            binding.txtQuantity.text = number.toString()
            firstClick = false
        } else {
            // Kiểm tra nếu đã có dấu chấm và chưa đủ 2 số sau dấu chấm, thì cộng số vào phần thập phân
            if (currentText.contains(".") && currentText.substringAfter(".").length < 2) {
                val quantity = currentText + number.toString()
                binding.txtQuantity.text = quantity
            } else if (!currentText.contains(".")) {
                // Nếu chưa có dấu chấm, thì cộng số vào phần nguyên
                val quantity = currentText + number.toString()
                // Kiểm tra giới hạn 999,999.999
                val formattedQuantity = quantity.replace(",", "").toDouble()
                if (formattedQuantity <1000000) {
                    binding.txtQuantity.text = formattedQuantity.formatNumber()
                } else {
                    binding.txtQuantity.text = "999,999.99"
                }
            }
        }
    }

    private fun appendDots() {
        val currentText = binding.txtQuantity.text.toString()
        if (!currentText.contains(".")) {
            binding.txtQuantity.append(".")
        }
    }

    private fun resetText() {
        binding.txtQuantity.text = "0"
    }

    private fun deleteText() {
        val quantity = binding.txtQuantity.text.toString()
        if (quantity.length > 1) {
            binding.txtQuantity.text = quantity.substring(0, quantity.length - 1)
        } else {
            binding.txtQuantity.text = "0"
        }
    }

}