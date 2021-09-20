package com.harshad.pizzordercart.itemclicklistener

interface ItemClickListener {

    fun onAddButtonCLick()
    fun onCustomiseButtonClick(position: Int)
    fun onIncrementButtonClick()
    fun onDecrementButtonClick()
    fun onSizeClick()
    fun onCrustClick()
}