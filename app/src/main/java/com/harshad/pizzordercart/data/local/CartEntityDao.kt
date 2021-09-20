package com.harshad.pizzordercart.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartEntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItemIntoCart(cartEntity: CartEntity): Long

    @Query("SELECT * FROM cartTable")
    fun getAllCart(): LiveData<List<CartEntity>>

    @Query("SELECT SUM (productQuantity) FROM cartTable")
    fun getCartCount(): LiveData<Int>

    @Query("SELECT SUM (productTotalSellPrice) FROM cartTable")
    fun getTotalCartAmount(): LiveData<Long>

    @Query("SELECT * FROM cartTable WHERE cId=:id")
    fun getCartEntityById(id: Long): LiveData<CartEntity>

    @Delete
    fun deleteItemFromCart(cartEntity: CartEntity)

    @Query("UPDATE cartTable SET productQuantity=:quantity,productTotalSellPrice=:price,productSize=:size WHERE cId=:id")
    fun updateQuantityPrice(quantity: Int, price: Double, size: String, id: Long)
}