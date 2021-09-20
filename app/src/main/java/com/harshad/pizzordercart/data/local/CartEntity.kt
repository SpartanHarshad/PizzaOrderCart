package com.harshad.pizzordercart.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartTable")
data class CartEntity(
    @ColumnInfo(name = "productTitle") var productTitle: String,
    @ColumnInfo(name = "productDesc") var productDesc: String,
    @ColumnInfo(name = "productSize") var productSize: String,
    @ColumnInfo(name = "productPerItemPrice") var productPerItemPrice: Double,
    @ColumnInfo(name = "productTotalSellPrice") var productTotalSellPrice: Double,
    @ColumnInfo(name = "productQuantity") var productQuantity: Int,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cId")
    var cId: Int? = null
}
