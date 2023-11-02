package com.example.snphmsapo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.snphmsapo.R

import com.example.snphmsapo.contract.MainActivityContract
import com.example.snphmsapo.presenter.MainActivityPresenter
import com.example.snphmsapo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

// thứ tự các biến và hàm trong activity
//public variable
//private variable
//hàm overive he thong
//hàm overive cua minh
//funcion
//private fun
//class vs adapter


class MainActivity : AppCompatActivity(), MainActivityContract.ShowData {
    private lateinit var binding: ActivityMainBinding
    private val presenter: MainActivityPresenter by lazy {
        MainActivityPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // khởi tạo viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = AdapterFragment(this)
        binding.vpgMain.adapter = adapter

        binding.btnvgtMain.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.itemOrder -> binding.vpgMain.currentItem = 0
                    R.id.itemProduct -> binding.vpgMain.currentItem = 1
                }
                return true
            }
        })
        // mặc định Fragment Product
        binding.btnvgtMain.selectedItemId = R.id.itemProduct



    }




}

