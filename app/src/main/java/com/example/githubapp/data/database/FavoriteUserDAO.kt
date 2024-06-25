package com.example.githubapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface FavoriteUserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: favoriteUser)

    @Delete
    fun delete(favorite: favoriteUser)

    @Query("SELECT * from favoriteUser")
    fun getAllFavoriteUser(): LiveData<List<favoriteUser>>

    @Query("DELETE FROM favoriteUser WHERE username = :username")
    fun deleteByUsername(username: String)

    @Query("SELECT EXISTS (SELECT 1 FROM favoriteUser WHERE username = :username)")
    fun isFavoriteUser(username: String): Boolean

}
