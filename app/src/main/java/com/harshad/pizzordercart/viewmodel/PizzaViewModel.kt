package com.harshad.pizzordercart.viewmodel

import androidx.lifecycle.*
import com.harshad.pizzordercart.data.local.CartEntity
import com.harshad.pizzordercart.data.model.ResponseModel
import com.harshad.pizzordercart.repository.PizzaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PizzaViewModel @Inject constructor(private val pizzaRepository: PizzaRepository) :
    ViewModel() {
    private var mockResponse = MutableLiveData<ResponseModel>()

    fun getPizza(): MutableLiveData<ResponseModel> {
        viewModelScope.launch {
            val response = pizzaRepository.getAllPizzaRepo()
            mockResponse.value = response.data!!
        }
        return mockResponse
    }

    suspend fun addProductModel(cartEntity: CartEntity): Long {
        return pizzaRepository.addProductRepo(cartEntity)
    }

    fun getAllItem(): LiveData<List<CartEntity>> {
        return pizzaRepository.getAllItemRepo()
    }

    fun updateProductQuantityModel(quantity: Int, price: Double, size: String, id: Long) {
        pizzaRepository.updateProductQuantityRepo(quantity, price, size, id)
    }

    fun getTotalCartAmountModel(): LiveData<Long> {
        return pizzaRepository.getTotalCartAmountRepo()
    }

    fun getCartCountModel(): LiveData<Int> {
        return pizzaRepository.getCartCountRepo()
    }

    fun getCartEntityByIdModel(id: Long): LiveData<CartEntity> {
        return pizzaRepository.getCartEntityByIdRepo(id)
    }

    fun deleteItemFromCartModel(cartEntity: CartEntity) {
        pizzaRepository.deleteItemFromCartRepo(cartEntity)
    }
}