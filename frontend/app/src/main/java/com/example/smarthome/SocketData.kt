package com.example.smarthome
import kotlinx.serialization.*

@Serializable
data class SocketData(val type:String, val temp_data:Double, val humid_data:Double)
