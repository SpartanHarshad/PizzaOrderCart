package com.harshad.pizzordercart.data.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Size(
    var id: Int,
    var name: String,
    var price: Double,
) : Parcelable