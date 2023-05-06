package com.example.smarthome

import com.google.gson.JsonObject
import android.content.Intent
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedRepository {

    suspend fun getCommandList(token:String):ArrayList<Command>?{
        val request = ServiceBuilder.getretrofit().create(APIInterface::class.java).getCommandList(token)

        if (request.isSuccessful){
            return request.body()!!
        }

        return null

    }


    suspend fun getIntentList(token: String):ArrayList<IntentClass>?{
        val request = ServiceBuilder.getretrofit().create(APIInterface::class.java).getIntentList(token)

        if (request.isSuccessful){
            return request.body()!!
        }

        return null
    }

    suspend fun getDetailIntent(token:String, slug:String):IntentClass?{
        val request = ServiceBuilder.getretrofit().create(APIInterface::class.java).getDetailIntent(token, slug)
        if (request.isSuccessful){
            return request.body()!!
        }

        return null
    }

//    fun postChat(prompt:String):BotResponse?{
//        var ans:BotResponse? = null
//        ServiceBuilder.getretrofit().create(APIInterface::class.java).postChat(promp).enqueue(object : Callback<BotResponse> {
//            override fun onResponse(call: Call<BotResponse>, response: Response<BotResponse>) {
//                val res = response.body()
//                Log.e("veryimport", "$res")
//                if (res != null){
//                    ans = res
//                }
//
//            }
//
//            override fun onFailure(call: Call<BotResponse?>, t: Throwable?) {
//
//            }
//        })
//
//        if (ans != null){
//            return ans
//        }
//
//        return null
//
//    }

    suspend fun getWeatherForecast():JsonObject?{
        val request = ServiceBuilder.getInstance().create(APIInterface::class.java).getWeatherForecast()

        if (request.isSuccessful){
            return request.body()!!
        }

        return null
    }

//    suspend fun postCommand(command: Command):String?{
//        val request = ServiceBuilder.getretrofit().create(APIInterface::class.java).postCommand(command)
//
//        if (request.isSuccessful){
//            return request.body()!!
//        }
//
//        return null
//    }

//    val client = HttpClient(CIO){
//        install(WebSockets){
//            pingInterval = 20_000
//        }
//    }

}
