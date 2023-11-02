package com.example.snphmsapo.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.snphmsapo.R
import com.example.snphmsapo.databinding.DialogDeleteBinding

class DialogDelete(
    context: Context,
    private val onNumberEntered: () -> Unit,
    private val name: String
) :
    Dialog(context) {
    private lateinit var binding: DialogDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtDeleteName.text = context.getString(R.string.DeleteItemOrder, name)
        binding.txtDeleteCancel.setOnClickListener { dismiss() }
        binding.txtDeleteConfirm.setOnClickListener {
            onNumberEntered()
            dismiss()
        }
    }
}