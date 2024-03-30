package com.example.githubapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubapp.data.database.FavoriteUserDAO
import com.example.githubapp.data.database.FavoriteUserRoomDatabase
import com.example.githubapp.data.database.favoriteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val mFavoriteUserDAO : FavoriteUserDAO
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init{
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDAO = db.favoriteDao()
    }

    fun getAllFavoriteUser() : LiveData<List<favoriteUser>> = mFavoriteUserDAO.getAllFavoriteUser()

    fun insert(favorite: favoriteUser){
        executorService.execute { mFavoriteUserDAO.insert(favorite) }
    }

    fun delete(favorite: favoriteUser){
        executorService.execute { mFavoriteUserDAO.delete(favorite) }
    }

    fun deleteByUsername(username: String) {
        executorService.execute { mFavoriteUserDAO.deleteByUsername(username) }
    }

    suspend fun isUserFavorite(username: String): Boolean {
        return withContext(Dispatchers.IO) {
            mFavoriteUserDAO.isFavoriteUser(username)
        }
    }


}