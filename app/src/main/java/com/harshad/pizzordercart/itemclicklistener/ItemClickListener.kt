package com.harshad.pizzordercart.itemclicklistener

import com.harshad.pizzordercart.data.local.CartEntity

interface ItemClickListener {

    fun incrementCount(cartEntity: CartEntity, position: Int)
    fun decrementCount(cartEntity: CartEntity, position: Int)
}