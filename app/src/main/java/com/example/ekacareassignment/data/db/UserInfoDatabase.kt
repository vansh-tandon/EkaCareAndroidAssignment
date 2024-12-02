package com.example.ekacareassignment.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ekacareassignment.data.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserInfoDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserInfoDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
           instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserInfoDatabase::class.java,
                "user_table"
            ).fallbackToDestructiveMigration()
                .build()
    }
}
