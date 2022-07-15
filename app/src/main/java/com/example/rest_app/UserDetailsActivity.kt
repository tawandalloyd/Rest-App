package com.example.rest_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rest_app.Repo.Repository
import com.example.rest_app.Repo.Users
import com.example.rest_app.Repo.getUserResults
import com.example.rest_app.utils.STATUS
import kotlinx.android.synthetic.main.activity_user_details.*
import java.util.*

class UserDetailsActivity : AppCompatActivity(), getUserResults {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val id = intent.getStringExtra("_id")
        val name = intent.getStringExtra("name")
        val username = intent.getStringExtra("username")


        delete.setOnClickListener {
            Repository.getInstance().deleteUsers(id,this)
        }

        textView3.text= name
        textView4.text = username
        textView2.text = id


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