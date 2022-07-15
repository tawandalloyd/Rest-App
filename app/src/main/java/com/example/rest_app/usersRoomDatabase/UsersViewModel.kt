package com.example.rest_app.usersRoomDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rest_app.Repo.Repository
import com.example.rest_app.Repo.Users
import com.example.rest_app.Repo.getUserResults
import com.example.rest_app.utils.STATUS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class UsersViewModel(application: Application):AndroidViewModel(application) , getUserResults {

    private val repository: UsersRepository
    private var mUsers: ArrayList<Users> = arrayListOf<Users>()
    private val users: LiveData<List<UsersEntity>>

    //Repository.getInstance().users
    private var users_: MutableLiveData<ArrayList<Users>> = MutableLiveData<ArrayList<Users>>()


    init {
        val usersDao = UsersRoomDatabase.getDatabase(application, viewModelScope).usersDao()
        repository = UsersRepository(usersDao)
        users = repository.allUsers

    }


    fun insert(users: UsersEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(users)
    }

    fun getAllUsers(): LiveData<List<UsersEntity>> {
        return users
    }

    fun getUsers(): LiveData<ArrayList<Users>> {
        init_()
        return users_
    }

    fun init_() {
        Repository.getInstance().getUsers(this)
        users_.value = mUsers
    }

    fun deleteUsers(id: String) {
        mUsers.forEachIndexed { index, element ->

                if (id == element._id) {
                    mUsers.removeAt(index)
                    users_.postValue(mUsers)
                    return
                }

            }
    }



    override fun allUsers(wasSuccessful: Boolean, tours: ArrayList<Users>?, status: STATUS?) {
        if (wasSuccessful) {
            if (tours != null) {
                mUsers.addAll(tours)

            }
            users_.postValue(mUsers)

        }
    }


    override fun deleteUsers(wasSuccessful: Boolean, id: String?, status: STATUS?) {
        TODO("Not yet implemented")
    }

    override fun CreateUsers(wasSuccessful: Boolean, tours: ArrayList<Users>?, status: STATUS?) {
        TODO("Not yet implemented")
    }

}

