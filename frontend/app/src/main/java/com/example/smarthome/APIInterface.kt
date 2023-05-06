package com.example.smarthome

import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
//import com.google.gson.JsonObject

interface APIInterface {

    @GET("chatbot/command_list")
    suspend fun getCommandList(@Header("Authorization") token:String):Response<ArrayList<Command>>


    @POST("auth")
    fun postUserLogin(
        @Body author:Authorization
    ):Call<Token>

    @GET("chatbot/intent_list")
    suspend fun getIntentList(@Header("Authorization") token: String):Response<ArrayList<IntentClass>>

    @GET("chatbot/intent/{slug}")
    suspend fun getDetailIntent(@Header("Authorization") token: String,
                                @Path("slug", encoded = true) slug:String):Response<IntentClass>

    
    @GET("/v1/forecast.json?key=f5ae91f09eab42ea8d332001230504&q=Ho Chi Minh&days=5&aqi=no&alerts=no")
    suspend fun getWeatherForecast():Response<JsonObject>

    @POST("chatbot/chat/")
    fun postChat(
        @Body prom:RequestBody
    ):Call<BotResponse>

//    @POST("chatbot/nan")
//    suspend fun postCommand(@Body command:Command):Response<String>
}