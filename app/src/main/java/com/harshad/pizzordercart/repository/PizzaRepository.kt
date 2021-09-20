package com.harshad.pizzordercart.repository

import android.util.Log
import com.harshad.pizzordercart.data.model.ResponseModel
import com.harshad.pizzordercart.data.remote.NetworkResource
import com.harshad.pizzordercart.data.remote.PizzaService
import javax.inject.Inject

class PizzaRepository @Inject constructor(private val pizzaService: PizzaService) {

    suspend fun getAllPizzaRepo(): NetworkResource<ResponseModel> {
        val response = try {
            pizzaService.fetchAllPizza()
        } catch (e: Exception) {
            return NetworkResource.Error("${e.localizedMessage}")
        }
        return NetworkResource.Success(response)
    }
}