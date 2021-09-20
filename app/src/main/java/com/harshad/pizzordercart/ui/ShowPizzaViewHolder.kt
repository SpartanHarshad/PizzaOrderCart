package com.harshad.pizzordercart.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.harshad.pizzordercart.data.model.ResponseModel
import com.harshad.pizzordercart.itemclicklistener.ItemClickListener


class ShowPizzaViewHolder(
    private val view: View,
    private val itemClickListener: ItemClickListener,
) : RecyclerView.ViewHolder(view) {

    fun setData(responseModel: ResponseModel) {
//        var defaultCrust = responseModel.defaultCrust
//        if (defaultCrust == 1) {
//            var defaultSize = responseModel.crusts?.get(0)?.defaultSize
//            view.tvPrice.text = "₹ ${responseModel!!.crusts[0].sizes!![defaultSize]?.price}"
//            view.btnCustomise.setOnClickListener {
//                responseModel.defaultCrust?.let { it1 ->
//                    itemClickListener.onCustomiseButtonClick(it1)
//                }
//            }
//            view.tvPizzaName.text = responseModel.name
//            view.tvPizzaDescription.text = responseModel.description
//            var sideArr = responseModel.crusts[0].sizes
//        }
    }
}