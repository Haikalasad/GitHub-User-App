package com.example.githubapp.ui.detail

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.repository.FavoriteUserRepository
import com.example.githubapp.data.response.DetailUserResponse
import com.example.githubapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(application,FavoriteUserRepository(application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") ?: ""

        viewModel.getDetailUser(username)
        viewModel.checkIfUserIsFavorite(username)

        viewModel.detailUser.observe(this, Observer { detailUser ->
            if (detailUser != null) {
                bindDetailUser(detailUser)
                viewModel.getListFollower(username)
                viewModel.getListFollowing(username)
            }
        })

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.isFavorite.observe(this, Observer { isFavorite ->
            val favoriteIcon = if (isFavorite == true) {
                R.drawable.baseline_favorite_24
            } else {
                R.drawable.baseline_favorite_border_24
            }
            binding.btnAddFavorite.setImageResource(favoriteIcon)
        })

        val tabs: TabLayout = findViewById(R.id.tabs)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val sectionPagerAdapter = SectionPagerAdapter(this)
        viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        val tabTextColorLight = ContextCompat.getColorStateList(this, R.color.black)
        val tabTextColorDark = ContextCompat.getColorStateList(this, R.color.white)
        val tabTextColor = if (isDarkTheme) tabTextColorDark else tabTextColorLight

        tabs.setTabTextColors(tabTextColor)

        binding.btnAddFavorite.setOnClickListener {
            val detailUser = viewModel.detailUser.value
            val username = detailUser?.login
            val avatarUrl = detailUser?.avatarUrl
            username?.let {
                if (avatarUrl != null) {
                    viewModel.toggleFavoriteStatus(it, avatarUrl)
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun bindDetailUser(detailUser: DetailUserResponse) {
        binding.tvName.text = detailUser.name
        binding.tvUsername.text = detailUser.login
        binding.tvFollower.text = "${detailUser.followers} Followers"
        binding.tvFollowing.text = "${detailUser.following} Following"
        Glide.with(binding.root.context).load(detailUser.avatarUrl).into(binding.imgItemPhoto)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
