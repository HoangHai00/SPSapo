package com.example.snphmsapo.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snphmsapo.databinding.FragmentOrderBinding


class FragmentOrder : Fragment() {

    private lateinit var binding: FragmentOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextListProductActivity()
    }


    private fun nextListProductActivity() {
        val nextListProduct: View.OnClickListener?
        nextListProduct = View.OnClickListener {
            val intent = Intent(requireContext(), ActivityOrder::class.java)
            startActivity(intent)
        }
        binding.constOrderOrder.setOnClickListener(nextListProduct)
        binding.ivbtnOrderOrder.setOnClickListener(nextListProduct)
    }


}