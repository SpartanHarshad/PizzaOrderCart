package com.harshad.pizzordercart.data.remote

import com.harshad.pizzordercart.data.model.ResponseModel
import retrofit2.http.GET

interface PizzaService {
    @GET("pizzas")//pizzas
    suspend fun fetchAllPizza(): ResponseModel
}