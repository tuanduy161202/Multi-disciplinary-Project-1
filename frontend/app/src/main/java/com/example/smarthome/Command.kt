package com.example.smarthome

import android.os.Parcel
import android.os.Parcelable

data class Command(var text:String, var intent_name:String):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(intent_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Command> {
        override fun createFromParcel(parcel: Parcel): Command {
            return Command(parcel)
        }

        override fun newArray(size: Int): Array<Command?> {
            return arrayOfNulls(size)
        }
    }
}

