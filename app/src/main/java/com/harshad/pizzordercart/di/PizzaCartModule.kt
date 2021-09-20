package com.harshad.pizzordercart.di

import android.app.Application
import androidx.room.Room
import com.harshad.pizzordercart.data.local.PizzaDatabase
import com.harshad.pizzordercart.data.remote.PizzaService
import com.harshad.pizzordercart.util.Constants.MOCK_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object PizzaCartModule {

    @Provides
    fun providesPizzaService(): PizzaService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(interceptor = interceptor).build())
            .baseUrl(MOCK_URL).build().create(PizzaService::class.java)
    }

    @Provides
    fun providesPizzaDatabase(app: Application): PizzaDatabase {
        return Room.databaseBuilder(app, PizzaDatabase::class.java, "pizzaDatabase")
            .fallbackToDestructiveMigration().build()
    }
}