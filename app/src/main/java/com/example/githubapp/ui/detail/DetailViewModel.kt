package com.example.githubapp.ui.detail

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.database.favoriteUser
import com.example.githubapp.data.repository.FavoriteUserRepository
import com.example.githubapp.data.response.DetailUserResponse
import com.example.githubapp.data.response.Follower
import com.example.githubapp.data.response.ListFollowingResponse
import com.example.githubapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val application: Application,private val favoriteUserRepository: FavoriteUserRepository) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean?>()
    val isFavorite: LiveData<Boolean?>
        get() = _isFavorite

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse>
        get() = _detailUser

    private val _listFollower = MutableLiveData<List<Follower>>()
    val listFollower: LiveData<List<Follower>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ListFollowingResponse>>()
    val listFollowing: LiveData<List<ListFollowingResponse>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isFollowerLoading = MutableLiveData<Boolean>()
    val isFollowerLoading: LiveData<Boolean>
        get() = _isFollowerLoading

    private val _isFollowingLoading = MutableLiveData<Boolean>()
    val isFollowingLoading: LiveData<Boolean>
        get() = _isFollowingLoading



    fun getDetailUser(username: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()

        apiService.getDetailUser(username).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getListFollower(username: String) {
        _isFollowerLoading.value = true
        val apiService = ApiConfig.getApiService()

        apiService.getListFollower(username).enqueue(object : Callback<List<Follower>> {
            override fun onResponse(call: Call<List<Follower>>, response: Response<List<Follower>>) {
                if (response.isSuccessful) {
                    _isFollowerLoading.value = false
                    _listFollower.value = response.body()
                } else {
                    _isFollowerLoading.value = false
                    _listFollower.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<Follower>>, t: Throwable) {
                _isFollowerLoading.value = false
                _listFollower.value = emptyList()
            }
        })
    }

    fun getListFollowing(username: String) {
        _isFollowingLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getListFollowing(username).enqueue(object : Callback<List<ListFollowingResponse>> {
            override fun onResponse(
                call: Call<List<ListFollowingResponse>>,
                response: Response<List<ListFollowingResponse>>
            ) {
                if (response.isSuccessful) {
                    _isFollowingLoading.value = false
                    _listFollowing.value = response.body()
                } else {
                    _isFollowingLoading.value = false
                    _listFollowing.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<ListFollowingResponse>>, t: Throwable) {
                _isFollowingLoading.value = false
                _listFollowing.value = emptyList()
            }
        })
    }
    fun checkIfUserIsFavorite(username: String) {
        viewModelScope.launch {
            val isFavorite = favoriteUserRepository.isUserFavorite(username)
            _isFavorite.postValue(isFavorite)
        }
    }

    fun toggleFavoriteStatus(username: String, avatarUrl: String) {
        viewModelScope.launch {
            try {
                if (_isFavorite.value == true) {
                    favoriteUserRepository.deleteByUsername(username)
                    _isFavorite.postValue(false)

                    Toast.makeText(application, "$username telah dihapus dari favorit", Toast.LENGTH_SHORT).show()
                } else {
                    val user = favoriteUser(username, avatarUrl)
                    favoriteUserRepository.insert(user)
                    _isFavorite.postValue(true)

                    Toast.makeText(application, "$username telah ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                println("Error")
            }
        }
    }

}
