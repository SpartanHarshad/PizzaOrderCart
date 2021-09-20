package com.harshad.pizzordercart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class], version = 1)
abstract class PizzaDatabase : RoomDatabase() {
    abstract fun getPizzaCartDao(): CartEntityDao
}