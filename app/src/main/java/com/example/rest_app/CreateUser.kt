package com.example.rest_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rest_app.Repo.Repository
import com.example.rest_app.Repo.Users
import com.example.rest_app.Repo.getUserResults
import com.example.rest_app.utils.STATUS
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.create_tour.*
import java.util.*

class CreateUser : AppCompatActivity(), getUserResults {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        add.setOnClickListener {
            addUser()
        }


    }

    private fun addUser() {
        val firstName = tourname.text.toString()

        val uName = username.text.toString()
        val eMail = email.text.toString()
        val phone = phone3.text.toString()
        val website = website4.text.toString()

        val user = Users(firstName, uName, eMail, phone,website)
        Repository.getInstance().addUsers(user,this)
    }

    override fun allUsers(wasSuccessful: Boolean, tours: ArrayList<Users>?, status: STATUS?) {
        TODO("Not yet implemented")
    }

    override fun deleteUsers(wasSuccessful: Boolean, id: String?, status: STATUS?) {
        TODO("Not yet implemented")
    }

    override fun CreateUsers(wasSuccessful: Boolean, tours: ArrayList<Users>?, status: STATUS?) {
        TODO("Not yet implemented")
    }


}