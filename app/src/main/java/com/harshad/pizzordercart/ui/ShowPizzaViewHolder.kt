package com.harshad.pizzordercart.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.harshad.pizzordercart.data.local.CartEntity
import com.harshad.pizzordercart.databinding.ItemLayoutBinding
import com.harshad.pizzordercart.itemclicklistener.ItemClickListener


class ShowPizzaViewHolder(
    val binding: ItemLayoutBinding,
    private val itemClickListener: ItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun setData(cartEntity: CartEntity, position: Int) {
        binding.apply {
            tvPriceItem.text = "₹ ${cartEntity.productPerItemPrice}"
            tvPizzaNameItem.text = "${cartEntity.productTitle}"
            tvPizzaSizeItem.text = "${cartEntity.productSize}"
            tvPizzaItemQuantity.text = "${cartEntity.productQuantity}"
            tvPizzaQuantityDecrease.setOnClickListener {
                itemClickListener.decrementCount(cartEntity, position)
            }
            tvPizzaQuantityIncrease.setOnClickListener {
                itemClickListener.incrementCount(cartEntity, position)
            }
        }
    }
}