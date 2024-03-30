package com.example.githubapp.ui.main

import GitListAdapter
import MainViewModel
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.data.datastore.SettingPreferences
import com.example.githubapp.data.datastore.dataStore
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GitListAdapter
    private lateinit var viewModel: MainViewModel

    private val prvquery = "Jhon"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)
        adapter = GitListAdapter()

        viewModel.getList(prvquery)

        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter

        updateFabIcon()

        binding.switchTheme.setOnClickListener {
            viewModel.toggleDarkMode()
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString().trim()
                viewModel.getList(query)
                searchView.hide()
                true
            }
        }

        viewModel.githubResponse.observe(this, Observer { githubResponse ->
            adapter.submitList(githubResponse.items)
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            showLoading(isLoading)
        })

        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        val favoriteMenuItem = menu?.findItem(R.id.btn_favorite)
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val iconResId2 = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> R.drawable.baseline_favorite_24
            Configuration.UI_MODE_NIGHT_YES -> R.drawable.baseline_favorite_24_dark
            else -> R.drawable.baseline_favorite_24
        }
        favoriteMenuItem?.setIcon(iconResId2)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun updateFabIcon() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val iconResId = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> R.drawable.baseline_light_mode_24
            Configuration.UI_MODE_NIGHT_YES -> R.drawable.baseline_dark_mode_24
            else -> R.drawable.baseline_light_mode_24
        }

        binding.switchTheme.setImageResource(iconResId)


    }



}



