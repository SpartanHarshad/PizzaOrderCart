package com.harshad.pizzordercart.data.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crust(
    var defaultSize: Int,
    var id: Int,
    var name: String,
    var sizes: List<Size>
) : Parcelable