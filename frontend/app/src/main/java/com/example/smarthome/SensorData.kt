package com.example.smarthome

import android.os.Parcel
import android.os.Parcelable

//data class SensorData(var dateTime: String, var temp: Float, var humid: Float): Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString()!!,
//        parcel.readFloat(),
//        parcel.readFloat(),
//    ){}
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(dateTime)
//        parcel.writeFloat(temp)
//        parcel.writeFloat(humid)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<SensorData> {
//        override fun createFromParcel(parcel: Parcel): SensorData {
//            return SensorData(parcel)
//        }
//
//        override fun newArray(size: Int): Array<SensorData?> {
//            return arrayOfNulls(size)
//        }
//    }
//}

data class SensorData(var date: String, var temperature: Float, var humidity: Float)
