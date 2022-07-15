package com.example.rest_app.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rest_app.Firestore.Firestore
import com.example.rest_app.MainActivity
import com.example.rest_app.R
import com.example.rest_app.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        //when user doesn't have account
        no_account.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        //login user on button click
        login.setOnClickListener {
            loginUser()
        }
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?){
        if (user== null){
            return
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun loginUser(){
        val email = emailtextview.text.toString()
        val password = passwordtextview.text.toString()

        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "email or password cannot be null", Toast.LENGTH_SHORT).show()
            return
        }
        else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                        task->
                    if (task.isSuccessful)
                    {

                        Log.d("MainActivity", "signInWithEmail:success")

                        val user = auth.currentUser
                        updateUI(user)
                        Firestore().getUserDetails(this)

                    } else
                    {
                        Toast.makeText(
                            this,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                .addOnFailureListener {
                    Log.d("SignUp","failed to create user ${it.message}")
                }
        }

    }

    fun userLoginSuccess(user: Users){
        Log.i("First Name", user.firstname)
        Log.i("LastName", user.lastname)
        Log.i("email",user.email)


    }
}