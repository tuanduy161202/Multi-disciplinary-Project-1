package com.example.smarthome


import kotlinx.coroutines.Dispatchers
import retrofit2.create

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

    suspend fun getDetailIntent(token:String, code:String):IntentClass?{
        val request = ServiceBuilder.getretrofit().create(APIInterface::class.java).getDetailIntent(token, code)

        if (request.isSuccessful){
            return request.body()!!
        }

        return null
    }

//    val client = HttpClient(CIO){
//        install(WebSockets){
//            pingInterval = 20_000
//        }
//    }

}
