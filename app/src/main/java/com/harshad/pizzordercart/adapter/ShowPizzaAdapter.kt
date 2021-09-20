package com.harshad.pizzordercart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harshad.pizzordercart.R
import com.harshad.pizzordercart.data.local.CartEntity
import com.harshad.pizzordercart.data.model.ResponseModel
import com.harshad.pizzordercart.databinding.ItemLayoutBinding
import com.harshad.pizzordercart.itemclicklistener.ItemClickListener
import com.harshad.pizzordercart.ui.ShowPizzaViewHolder

class ShowPizzaAdapter(
    private val cartItems: List<CartEntity>,
    private val itemClickListener: ItemClickListener,
) : RecyclerView.Adapter<ShowPizzaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowPizzaViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowPizzaViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ShowPizzaViewHolder, position: Int) {
        var dataModel = cartItems[position]
        holder.setData(dataModel, position)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}