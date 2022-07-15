package com.example.rest_app.usersRoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users_details: UsersEntity)

    @Query("SELECT * from users ")
    fun getAllUsers(): LiveData<List<UsersEntity>>

    @Update
    suspend fun update(user_details: UsersEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()


}