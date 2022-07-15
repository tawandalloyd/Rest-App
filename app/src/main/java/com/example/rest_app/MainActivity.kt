package com.example.rest_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rest_app.Repo.Repository
import com.example.rest_app.authentication.Login
import com.example.rest_app.usersRoomDatabase.UsersEntity
import com.example.rest_app.usersRoomDatabase.UsersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    private var hasObserved = false
    private lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab: View = findViewById(R.id.fab)

        usersViewModel = ViewModelProvider(this)[UsersViewModel::class.java]

        setSupportActionBar(my_toolbar)
       // insertToRoom()

        bindTours()

        fab.setOnClickListener { view ->
            val intent = Intent(this, CreateUser::class.java)
            startActivity(intent)
        }

    }


    // inserting data to room
    private fun insertToRoom() {
        val usersViewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        val queue = Volley.newRequestQueue(application)
        queue.cache.clear()
        val url = "https://jsonplaceholder.typicode.com/users"
        val getUsers = object : StringRequest(
            Method.GET,
            url,
            Response.Listener {
                val res = JSONArray(it)
               // val chats = res.getJSONArray("messages")
                //Log.d("users", "users$res")
                for (i in 0 until res.length() ) {
                    val user = res.getJSONObject(i)
                    val id = user.get("id").toString()
                    val name = user.get("name").toString()
                    val username = user.get("username").toString()
                    val email = user.get("email").toString()
                    val address = user.getJSONObject("address")
                    val street = address.get("street").toString()
                    val suite = address.get("suite").toString()
                    val city = address.get("city").toString()
                    val zipCode = address.get("zipcode").toString()
                    val phone = user.get("phone").toString()
                    val website = user.get("website").toString()
                    val company = user.getJSONObject("company")
                    val companyName = company.get("name").toString()
                    val phrase = company.get("catchPhrase").toString()
                    val bs = company.get("bs").toString()

                   // Log.d("users","users$street")

                    val users = UsersEntity(id =id,name = name,username = username,email = email,street = street,suite = suite,city= city ,zipcode = zipCode ,phone = phone,website=website,companyName = companyName, phrase = phrase, bs = bs)
                     usersViewModel.insert(users)
                     Log.d("users","users$users")
                }

            },
            Response.ErrorListener {
                Log.d("error","$it")
            }
        )
        {
        }

        // Add the request to the RequestQueue.
        queue.add(getUsers)

    }




    private fun bindTours(){
        //Observer tours
        usersViewModel.getUsers().observe(
            this, Observer {
                value ->
                value.let {
                    val adapter = MainAdapter(value)
                    val recycleView = findViewById<RecyclerView>(R.id.recycle)
                    recycleView?.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    recycleView?.adapter = adapter

                    adapter.notifyDataSetChanged()
                    adapter.setOnItemClickListener(object : MainAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
                            intent.putExtra("name", value.get(position).name)
                            intent.putExtra("_id",value.get(position)._id)
                            startActivity(intent)

                            Log.d("item", "userID : $position")
                        }

                    })
                }
            }
        )

    }

    override fun onResume(){
        super.onResume();
        // put your code here...
        var id: String? = Repository.getInstance().deleted ?: return

        if (id != null) {
            usersViewModel.deleteUsers(id)
            Repository.getInstance().deleted = null
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                 logoutUser()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun logoutUser(){
        Firebase.auth.signOut()
        val intent = Intent(this,Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}