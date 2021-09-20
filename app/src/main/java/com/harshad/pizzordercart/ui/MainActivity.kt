package com.harshad.pizzordercart.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.harshad.pizzordercart.R
import com.harshad.pizzordercart.data.model.Crust
import com.harshad.pizzordercart.data.model.ResponseModel
import com.harshad.pizzordercart.data.model.Size
import com.harshad.pizzordercart.databinding.ActivityMainBinding
import com.harshad.pizzordercart.viewmodel.PizzaViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    val viewModel: PizzaViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    var responseModel: ResponseModel = ResponseModel(listOf(), 0, "", false, "")
    var defaultCrustName = ""
    var defaultPrice: Double = 0.0
    var defaultSize = ""
    var crustList = listOf<Crust>()
    var sizeList = listOf<Size>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callApi()
    }

    private fun setValuesToViews() {
        if (responseModel.isVeg) {
            binding.ivIsVegIcon.visibility = View.VISIBLE
            binding.ivIsNonVegIcon.visibility = View.GONE
        } else {
            binding.ivIsVegIcon.visibility = View.GONE
            binding.ivIsNonVegIcon.visibility = View.VISIBLE
        }
        binding.tvPrice.text = "₹ $defaultPrice"
        binding.tvPizzaName.text = responseModel.name
        binding.tvPizzaDescription.text = responseModel.description
        binding.btnCustomise.setOnClickListener(this)
        binding.btnAddIntoCart.setOnClickListener(this)
    }

    private fun getDefaultPizza() {
        var dCrust = responseModel.defaultCrust
        crustList = responseModel.crusts
        for (i in 0 until crustList.size) {
            if (crustList[i].id == dCrust) {
                defaultCrustName = crustList[i].name
                var dSize = crustList[i].defaultSize
                sizeList = crustList[i].sizes
                for (j in 0 until sizeList.size) {
                    if (sizeList[j].id == dSize) {
                        defaultSize = sizeList[j].name
                        defaultPrice = sizeList[j].price
                    }
                }
            }
        }
        setValuesToViews()
    }

    private fun callApi() {
        viewModel.getPizza().observe(this, Observer {
            responseModel = it
            getDefaultPizza()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCustomise -> {
                Toast.makeText(this, "Customise pizza", Toast.LENGTH_SHORT).show()
            }
            R.id.btnAddIntoCart -> {
                Toast.makeText(this, "Add pizza Into Cart", Toast.LENGTH_SHORT).show()
            }
        }
    }
}