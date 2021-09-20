package com.harshad.pizzordercart.data.remote


sealed class NetworkResource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : NetworkResource<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResource<T>(data, message)
    class Loading<T>(data: T? = null) : NetworkResource<T>(data)
}


