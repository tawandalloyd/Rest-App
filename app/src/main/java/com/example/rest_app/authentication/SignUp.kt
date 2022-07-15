package com.example.rest_app.authentication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rest_app.Firestore.Firestore
import com.example.rest_app.MainActivity
import com.example.rest_app.R
import com.example.rest_app.models.UsersFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // if user is already registered navigate to login screen
        already_registered.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        auth = Firebase.auth

        signUp.setOnClickListener {
           registerUser()
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
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun registerUser(){
        val email =  userEmail.text.toString()
        val password = userPassword.text.toString()
        val confirm = confirm_password.text.toString()

        if (confirm!=password){
            Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }
        if (email.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "email and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        //authenticating with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                    task->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val firebaseUser: FirebaseUser = task.result!!.user!!

                    val person = UsersFirestore(
                        firebaseUser.uid,
                        name.text.toString().trim(),
                        lastname.text.toString().trim(),
                        userEmail.text.toString().trim()
                    )

                    Firestore().registerUser(this,person)

                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
            .addOnFailureListener {
                Log.d("MainActivity","failed to create user ${it.message}")
            }
    }

    fun userRegistrationSuccess(){
        Toast.makeText(this, "registration success", Toast.LENGTH_SHORT).show()
    }



}