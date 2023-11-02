package com.example.snphmsapo.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.snphmsapo.R
import com.example.snphmsapo.databinding.DialogCancelOrderBinding


class DialogCancelOrder(
    context: Context,
    private val onNumberEntered: () -> Unit
) :
    Dialog(context) {
    private lateinit var binding: DialogCancelOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCancelOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtCancelOrderCancel.setOnClickListener { dismiss() }
        binding.txtCancelOrderConfirm.setOnClickListener {
            onNumberEntered()
            dismiss()
        }
    }
}