package com.example.githubapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [favoriteUser::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {

    abstract fun favoriteDao() : FavoriteUserDAO

    companion object{
        @Volatile
        private var INSTANCE : FavoriteUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context):FavoriteUserRoomDatabase{
            if (INSTANCE == null){
                synchronized(FavoriteUserRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserRoomDatabase::class.java,"favorite_database").build()
                }
            }
            return  INSTANCE as FavoriteUserRoomDatabase
        }
    }
}