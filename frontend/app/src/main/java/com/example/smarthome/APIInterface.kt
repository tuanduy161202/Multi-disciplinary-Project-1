package com.example.smarthome

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface APIInterface {

    @GET("chatbot/command_list")
    suspend fun getCommandList(@Header("Authorization") token:String):Response<ArrayList<Command>>


    @POST("auth")
    fun postUserLogin(
        @Body author:Authorization
    ):Call<Token>

    @GET("chatbot/intent_list")
    suspend fun getIntentList(@Header("Authorization") token: String):Response<ArrayList<IntentClass>>

    @GET("chatbot/intent/{code}")
    suspend fun getDetailIntent(@Header("Authorization") token: String,
                                @Path("code") code:String):Response<IntentClass>
}