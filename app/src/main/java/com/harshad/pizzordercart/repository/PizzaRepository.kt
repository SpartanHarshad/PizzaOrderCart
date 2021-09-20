package com.harshad.pizzordercart.repository

import androidx.lifecycle.LiveData
import com.harshad.pizzordercart.data.local.CartEntity
import com.harshad.pizzordercart.data.local.PizzaDatabase
import com.harshad.pizzordercart.data.model.ResponseModel
import com.harshad.pizzordercart.data.remote.NetworkResource
import com.harshad.pizzordercart.data.remote.PizzaService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class PizzaRepository @Inject constructor(
    private val pizzaService: PizzaService,
    private val database: PizzaDatabase,
) {
    private val cartDao = database.getPizzaCartDao()

    suspend fun getAllPizzaRepo(): NetworkResource<ResponseModel> {
        val response = try {
            pizzaService.fetchAllPizza()
        } catch (e: Exception) {
            return NetworkResource.Error("${e.localizedMessage}")
        }
        return NetworkResource.Success(response)
    }

    suspend fun addProductRepo(cartEntity: CartEntity): Long {
        return try {
            cartDao.addItemIntoCart(cartEntity)
        } catch (e: Exception) {
            return 0
        }
    }

    fun getAllItemRepo(): LiveData<List<CartEntity>> {
        return cartDao.getAllCart()
    }

    fun getTotalCartAmountRepo(): LiveData<Long> {
        return cartDao.getTotalCartAmount()
    }

    fun getCartCountRepo(): LiveData<Int> {
        return cartDao.getCartCount()
    }

    fun deleteItemFromCartRepo(cartEntity: CartEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.deleteItemFromCart(cartEntity)
        }
    }

    fun updateProductQuantityRepo(quantity: Int, price: Double, size: String, id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.updateQuantityPrice(quantity, price, size, id)
        }
    }

    fun getCartEntityByIdRepo(id: Long): LiveData<CartEntity> {
        return cartDao.getCartEntityById(id)
    }
}