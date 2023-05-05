package com.example.smarthome

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {


    fun getretrofit():Retrofit{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        val client = OkHttpClient().newBuilder()
        client.addInterceptor(logging)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/").client(client.build())
            .addConverterFactory(GsonConverterFactory.create()).build()

        return retrofit
    }

    fun getInstance():Retrofit {
        val baseUrl = "http://api.weatherapi.com/"
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}