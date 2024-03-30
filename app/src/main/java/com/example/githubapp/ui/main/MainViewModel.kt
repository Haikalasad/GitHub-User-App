import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.datastore.SettingPreferences
import com.example.githubapp.data.response.GithubResponse
import com.example.githubapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _githubResponse = MutableLiveData<GithubResponse>()
    val githubResponse: LiveData<GithubResponse> = _githubResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDarkModeActive = MutableLiveData<Boolean>()
    val isDarkModeActive: LiveData<Boolean> = _isDarkModeActive

    init {
        viewModelScope.launch {
            pref.getThemeSetting().collect {
                _isDarkModeActive.value = it
            }
        }
    }

    fun getList(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListData(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun toggleDarkMode() {
        val isDarkModeActive = _isDarkModeActive.value ?: true
        saveThemeSetting(!isDarkModeActive)
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
