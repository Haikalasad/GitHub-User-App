package com.example.githubapp.ui.favorite

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.database.favoriteUser
import com.example.githubapp.data.repository.FavoriteUserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val application: Application,private val favoriteUserRepository: FavoriteUserRepository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val favoriteUsers: LiveData<List<favoriteUser>> = favoriteUserRepository.getAllFavoriteUser()

    fun setLoading(loading: Boolean) {
        _isLoading.value =loading
    }

    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            favoriteUserRepository.deleteByUsername(username)
            Toast.makeText(application, "$username telah dihapus dari favorit", Toast.LENGTH_SHORT).show()
        }
    }


}