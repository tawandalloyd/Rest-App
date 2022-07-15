package com.example.rest_app.usersRoomDatabase

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UsersRepository(private  val usersDao: UsersDao) {
    val allUsers: LiveData<List<UsersEntity>> = usersDao.getAllUsers()

    @WorkerThread
    suspend fun insert(usersDetail: UsersEntity) {
        usersDao.insert(usersDetail)
    }



}