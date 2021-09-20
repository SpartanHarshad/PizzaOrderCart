package com.harshad.pizzordercart.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
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
}