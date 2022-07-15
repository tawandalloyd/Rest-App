package com.example.rest_app.Firestore

import android.app.Activity
import android.util.Log
import com.example.rest_app.authentication.Login
import com.example.rest_app.authentication.SignUp
import com.example.rest_app.models.Users
import com.example.rest_app.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User

class Firestore {

    private  val mFirestore  =  FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUp, userInfo : Users){
        //the "users" is collection name
        mFirestore.collection(Constants.USERS)
            //document id for user fields
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering user",
                    e
                )
            }
    }
    fun getCurrentUserID(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser!=null){
            currentUserID= currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity : Activity){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName,document.toString())
                val user = document.toObject(Users::class.java)

                when(activity){
                    is Login -> {
                        if (user != null) {
                            activity.userLoginSuccess(user)
                        }
                    }

                }

            }
    }


}