package com.harshad.pizzordercart.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    var dCrust = 0
    var dSize = 0;
    var crust_id = 0
    var size_id = 0
    var crustList = mutableListOf<Crust>()
    var sizeList = mutableListOf<Size>()

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
        binding.tvPrice.text = "₹ ${responseModel.crusts[dCrust].sizes.get(dSize).price}"
        binding.tvPizzaName.text = responseModel.crusts[dCrust].name
        binding.tvPizzaDescription.text = responseModel.description
        binding.btnCustomise.setOnClickListener(this)
        binding.btnAddIntoCart.setOnClickListener(this)
    }

    private fun getDefaultPizza() {
        dCrust = responseModel.defaultCrust
        crustList.addAll(responseModel.crusts)
        for (i in 0 until crustList.size) {
            if (crustList[i].id == dCrust) {
                defaultCrustName = crustList[i].name
                dSize = crustList[i].defaultSize
                sizeList.addAll(crustList[i].sizes)
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

            }
            R.id.btnAddIntoCart -> {
                showCrust()
            }
        }
    }

    private fun showCrust() {
        var crusts = getCrustList()
        val builder = AlertDialog.Builder(this)
        val checkedItem = dCrust - 1
        builder.setTitle("Choose Crust")
        var pos = 0
        builder.setSingleChoiceItems(crusts, checkedItem) { dialog, which ->
            pos = which
        }
        builder.setPositiveButton("OK") { dialog, which ->
            showCrustSize(pos)
            Toast.makeText(this, "$pos", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun showCrustSize(id: Int) {
        var sizes = getSizeForSpecificCrust(id)
        val builder = AlertDialog.Builder(this)
        val checkedItem = id - 1
        builder.setTitle("Choose Size")
        var pos = 0
        builder.setSingleChoiceItems(sizes, checkedItem) { dialog, which ->
            pos = which + 1
        }
        builder.setPositiveButton("OK") { dialog, which ->
            addCrustIntoCart(id, pos)
            crust_id = id
            size_id = pos
        }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * in this method we are adding pizza into cart
     */
    private fun addCrustIntoCart(crust_id: Int, size_id: Int) {
        var name = crustList.get(crust_id).name
        var size = crustList.get(crust_id).sizes.get(size_id - 1).name
        var price = crustList.get(crust_id).sizes.get(size_id - 1).price
        Toast.makeText(this, "$name | $size | $price", Toast.LENGTH_SHORT).show()
    }

    /**
     * in this method we are returning array of crust to show in dialog
     */
    private fun getCrustList(): Array<String> {
        var crusts = mutableListOf<String>()
        for (i in 0 until crustList.size) {
            crusts.add(crustList[i].name)
        }
        return crusts.toTypedArray()
    }

    /**
     * in this method we are returning array of size to show in dialog with given crust_id
     */
    private fun getSizeForSpecificCrust(crust_id: Int): Array<String> {
        var crustSizes = mutableListOf<String>()
        var c = crustList
        var len = crustList.get(crust_id).sizes.size
        for (i in 0 until len) {
            crustSizes.add(crustList.get(crust_id).sizes.get(i).name)
        }
        return crustSizes.toTypedArray()
    }
}