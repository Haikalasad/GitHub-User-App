import com.example.githubapp.data.response.DetailUserResponse
import com.example.githubapp.data.response.Follower
import com.example.githubapp.data.response.GithubResponse
import com.example.githubapp.data.response.ItemsItem
import com.example.githubapp.data.response.ListFollowingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListData(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getListFollower(@Path("username") username: String):Call<List<Follower>>

    @GET("users/{username}/following")
    fun getListFollowing(@Path("username") username: String):Call<List<ListFollowingResponse>>

}
