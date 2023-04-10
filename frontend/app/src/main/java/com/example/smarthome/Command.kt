package com.example.smarthome

import android.os.Parcel
import android.os.Parcelable

data class Command(var url:String, var command_id: Int, var text:String, var intent:String):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeInt(command_id)
        parcel.writeString(text)
        parcel.writeString(intent)
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

