package com.harshad.pizzordercart.ui

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.harshad.pizzordercart.R
import com.harshad.pizzordercart.adapter.ShowPizzaAdapter
import com.harshad.pizzordercart.data.local.CartEntity
import com.harshad.pizzordercart.data.model.Crust
import com.harshad.pizzordercart.data.model.ResponseModel
import com.harshad.pizzordercart.databinding.ActivityMainBinding
import com.harshad.pizzordercart.itemclicklistener.ItemClickListener
import com.harshad.pizzordercart.viewmodel.PizzaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val viewModel: PizzaViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    var responseModel: ResponseModel = ResponseModel(listOf(), 0, "", false, "")
    var cartEntity = CartEntity("", "", "", 0.0, 0.0, 1)
    var defaultCrustName = ""
    var defaultPrice: Double = 0.0
    var dCrust = 0
    var dSize = 0;
    var crust_id = 0
    var size_id = 0
    var cart_id: Long = 0
    var crustList = mutableListOf<Crust>()
    var productList = mutableListOf<CartEntity>()
    lateinit var showPizzaAdapter: ShowPizzaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeCart()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        showPizzaAdapter = ShowPizzaAdapter(productList, this)
        binding.rvCartList.layoutManager = LinearLayoutManager(this)
        binding.rvCartList.adapter = showPizzaAdapter
    }

    private fun observeCart() {
        viewModel.getCartCountModel().observe(this, Observer {
            if (it != null) {
                binding.tvCartTotalCount.text = it.toString()
            }
        })
        viewModel.getTotalCartAmountModel().observe(this, Observer {
            if (it != null) {
                binding.tvCartTotalPrice.text = "₹ $it"
            }
        })
        viewModel.getAllItem().observe(this, Observer {
            productList.clear()
            productList.addAll(it)
            showPizzaAdapter.notifyDataSetChanged()
        })

        if (checkInternetConnectivity(this)) {
            callApi()
            Toast.makeText(this, "Internet Is connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,
                "No internet connection Please Connect With Internet And Restart the app",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValuesToViews() {
        if (responseModel.isVeg) {
            binding.ivIsVegIcon.visibility = View.VISIBLE
            binding.ivIsNonVegIcon.visibility = View.GONE
        } else {
            binding.ivIsVegIcon.visibility = View.GONE
            binding.ivIsNonVegIcon.visibility = View.VISIBLE
        }
        binding.tvPrice.text = "₹ ${defaultPrice}"
        binding.tvPizzaName.text = defaultCrustName
        binding.tvPizzaDescription.text = responseModel.description
        binding.btnCustomise.setOnClickListener(this)
        binding.btnAddIntoCart.setOnClickListener(this)
        binding.tvIncreaseQuantity.setOnClickListener(this)
        binding.tvDecreaseQuantity.setOnClickListener(this)
    }

    private fun getDefaultPizza() {
        dCrust = responseModel.defaultCrust
        dSize = responseModel.crusts[dCrust - 1].defaultSize
        crustList.addAll(responseModel.crusts)
        defaultCrustName = responseModel.crusts[dCrust - 1].name
        defaultPrice = responseModel.crusts[dCrust - 1].sizes.get(dSize - 1).price
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
                Toast.makeText(this, "We Are Working On It", Toast.LENGTH_SHORT).show()
            }
            R.id.btnAddIntoCart -> {
                showCrust()
            }
            R.id.tvIncreaseQuantity -> {
                incrementQuantity(cartEntity)
            }
            R.id.tvDecreaseQuantity -> {
                decrementQuantity(cartEntity)
            }
        }
    }

    private fun incrementQuantity(cartEntity: CartEntity) {
        var itemQ = cartEntity.productQuantity + 1
        var price: Double = cartEntity.productPerItemPrice * itemQ
        var size = cartEntity.productSize
        viewModel.updateProductQuantityModel(itemQ, price, size, cartEntity.cId!!.toLong())
        binding.tvItemQuantity.text = "$itemQ"
    }

    private fun decrementQuantity(cartEntity: CartEntity) {
        if (cartEntity.productQuantity != 1) {
            var itemQ = cartEntity.productQuantity - 1
            var price: Double = cartEntity.productPerItemPrice * itemQ
            var size = cartEntity.productSize
            viewModel.updateProductQuantityModel(itemQ, price, size, cartEntity.cId!!.toLong())
            binding.tvItemQuantity.text = "$itemQ"
        } else {
            viewModel.deleteItemFromCartModel(cartEntity)
            binding.btnAddIntoCart.visibility = View.VISIBLE
            binding.changeQuantity.visibility = View.GONE
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
        var pos = id - 1
        builder.setSingleChoiceItems(sizes, checkedItem) { dialog, which ->
            pos = which + 1
        }
        builder.setPositiveButton("Add To Cart") { dialog, which ->
            addCrustIntoCart(id, pos)
            crust_id = id
            size_id = pos
            dSize = pos
        }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * in this method we are adding pizza into cart and updating ui
     */
    private fun addCrustIntoCart(crust_id: Int, size_id: Int) {
        if (size_id != 0) {
            var name = crustList.get(crust_id).name
            var size = crustList.get(crust_id).sizes.get(size_id - 1).name
            var price = crustList.get(crust_id).sizes.get(size_id - 1).price
            var cartItem = CartEntity(name, responseModel.description, size, price, price, 1)
            CoroutineScope(Dispatchers.IO).launch {
                var cid = viewModel.addProductModel(cartItem)
                setCartId(cid)
            }
            binding.tvPrice.text = "₹ ${price}"
            binding.tvPizzaName.text = name
            binding.changeQuantity.visibility = View.VISIBLE
            binding.btnAddIntoCart.visibility = View.GONE
        } else {
            var name = crustList.get(crust_id).name
            var size = crustList.get(crust_id).sizes.get(size_id).name
            var price = crustList.get(crust_id).sizes.get(size_id).price
            var cartItem = CartEntity(name, responseModel.description, size, price, price, 1)
            CoroutineScope(Dispatchers.IO).launch {
                var cid = viewModel.addProductModel(cartItem)
                setCartId(cid)
            }
            binding.tvPrice.text = "₹ ${price}"
            binding.tvPizzaName.text = name
            binding.changeQuantity.visibility = View.VISIBLE
            binding.btnAddIntoCart.visibility = View.GONE
        }
    }

    private fun setCartId(cid: Long) {
        cart_id = cid
        CoroutineScope(Dispatchers.Main).launch {
            setCart()
        }
    }

    fun setCart() {
        viewModel.getCartEntityByIdModel(cart_id).observe(this, Observer {
            if (it != null) {
                cartEntity = it
            }
        })
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
        var len = crustList.get(crust_id).sizes.size
        for (i in 0 until len) {
            crustSizes.add(crustList.get(crust_id).sizes.get(i).name)
        }
        return crustSizes.toTypedArray()
    }

    override fun incrementCount(cartEntity: CartEntity, position: Int) {
        var itemQ = cartEntity.productQuantity + 1
        var price: Double = cartEntity.productPerItemPrice * itemQ
        var size = cartEntity.productSize
        viewModel.updateProductQuantityModel(itemQ, price, size, cartEntity.cId!!.toLong())
        showPizzaAdapter.notifyItemChanged(position)
    }

    override fun decrementCount(cartEntity: CartEntity, position: Int) {
        if (cartEntity.productQuantity != 1) {
            var itemQ = cartEntity.productQuantity - 1
            var price: Double = cartEntity.productPerItemPrice * itemQ
            var size = cartEntity.productSize
            viewModel.updateProductQuantityModel(itemQ, price, size, cartEntity.cId!!.toLong())
            showPizzaAdapter.notifyItemChanged(position)
        } else {
            viewModel.deleteItemFromCartModel(cartEntity)
            showPizzaAdapter.notifyItemChanged(position)
        }
    }

    private fun checkInternetConnectivity(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        var isConnected = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}