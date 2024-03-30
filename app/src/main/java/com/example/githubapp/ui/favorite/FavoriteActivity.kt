package com.example.githubapp.ui.favorite

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.data.repository.FavoriteUserRepository
import com.example.githubapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteListAdapter
    private val viewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(application,FavoriteUserRepository(application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteListAdapter(viewModel)
        binding.rvListFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvListFavorite.adapter = adapter
        viewModel.setLoading(true)

        viewModel.favoriteUsers.observe(this) { users ->
            Handler().postDelayed({
                viewModel.setLoading(false)
                adapter.submitList(users)
            }, 1500)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

    }
    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

}
