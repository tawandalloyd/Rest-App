package com.example.rest_app.usersRoomDatabase

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity (
    @PrimaryKey val id: String,
    val name: String,
    val username: String,
    val email: String,
    //val address: String,
    val street : String,
    val suite : String,
    val city : String,
    val zipcode : String,
    val phone: String,
    val website: String,
    //val company : String,
    val companyName: String,
    val phrase : String,
    val bs : String

        ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(street)
        parcel.writeString(suite)
        parcel.writeString(city)
        parcel.writeString(zipcode)
        parcel.writeString(phone)
        parcel.writeString(website)
        parcel.writeString(companyName)
        parcel.writeString(phrase)
        parcel.writeString(bs)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsersEntity> {
        override fun createFromParcel(parcel: Parcel): UsersEntity {
            return UsersEntity(parcel)
        }

        override fun newArray(size: Int): Array<UsersEntity?> {
            return arrayOfNulls(size)
        }
    }
}