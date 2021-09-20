package com.harshad.pizzordercart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harshad.pizzordercart.R
import com.harshad.pizzordercart.data.model.ResponseModel
import com.harshad.pizzordercart.itemclicklistener.ItemClickListener
import com.harshad.pizzordercart.ui.ShowPizzaViewHolder

class ShowPizzaAdapter(
    private val responseModels: List<ResponseModel>,
    private val itemClickListener: ItemClickListener,
) : RecyclerView.Adapter<ShowPizzaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowPizzaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ShowPizzaViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ShowPizzaViewHolder, position: Int) {
        var dataModel = responseModels[position]
        holder.setData(dataModel)
    }

    override fun getItemCount(): Int {
        return responseModels.size
    }
}