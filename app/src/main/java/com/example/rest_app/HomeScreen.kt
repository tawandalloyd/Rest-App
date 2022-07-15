package com.example.rest_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.rest_app.authentication.Login
import com.example.rest_app.authentication.SignUp
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //navigate to the login screen
        SignIn.setOnClickListener {
            val intent =  Intent(this,Login::class.java)
            startActivity(intent)
        }

        //navigate to the registration page
      register.setOnClickListener {
          val intent = Intent(this, SignUp::class.java)
          startActivity(intent)
      }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,

            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )
    }
}