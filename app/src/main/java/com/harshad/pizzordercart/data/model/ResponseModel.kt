package com.harshad.pizzordercart.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseModel(
    var crusts: List<Crust>,
    var defaultCrust: Int,
    var description: String,
    var isVeg: Boolean,
    var name: String,
) : Parcelable