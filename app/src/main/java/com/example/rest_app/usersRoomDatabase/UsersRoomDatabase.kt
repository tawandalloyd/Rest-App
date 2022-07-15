package com.example.rest_app.usersRoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [UsersEntity::class], version = 1)
abstract class UsersRoomDatabase : RoomDatabase(){
    abstract fun usersDao(): UsersDao


    companion object {
        @Volatile
        private var INSTANCE: UsersRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UsersRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersRoomDatabase::class.java,
                    "users_database"
                ).fallbackToDestructiveMigration().addCallback(UsersDatabaseCallback(scope))
                    .build()
                INSTANCE = instance

                return instance
            }
        }

        private class UsersDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {



                        }
                    }
                }
            }
        }
    }
