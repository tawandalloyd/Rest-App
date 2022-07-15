package com.example.rest_app.models

import android.os.Parcel
import android.os.Parcelable

data class UsersFirestore (
    val  id : String = "",
    val firstname : String = "",
    val lastname : String = "",
    val email : String =""

        ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(firstname)
        parcel.writeString(lastname)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsersFirestore> {
        override fun createFromParcel(parcel: Parcel): UsersFirestore {
            return UsersFirestore(parcel)
        }

        override fun newArray(size: Int): Array<UsersFirestore?> {
            return arrayOfNulls(size)
        }
    }
}