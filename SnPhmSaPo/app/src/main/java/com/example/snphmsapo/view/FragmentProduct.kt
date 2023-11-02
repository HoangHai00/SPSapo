package com.example.snphmsapo.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snphmsapo.databinding.FragmentProductBinding


class FragmentProduct : Fragment() {


    private lateinit var binding: FragmentProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextListProductActivity()
    }



    private fun nextListProductActivity() {
        val nextListProduct: View.OnClickListener?
        nextListProduct = View.OnClickListener {
            val intent = Intent(requireContext(), ActivityListProduct::class.java)
            startActivity(intent)
        }
        binding.llMainList1.setOnClickListener(nextListProduct)
        binding.ivbtnMainList1.setOnClickListener(nextListProduct)
        binding.ivbtnMainNext1.setOnClickListener(nextListProduct)
    }



}