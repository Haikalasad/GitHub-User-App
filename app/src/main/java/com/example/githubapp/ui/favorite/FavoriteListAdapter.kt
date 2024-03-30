package com.example.githubapp.ui.favorite

import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.database.favoriteUser
import com.example.githubapp.databinding.ItemRowFavoriteBinding
import com.example.githubapp.ui.detail.DetailActivity

class FavoriteListAdapter(private val viewModel: FavoriteViewModel) : ListAdapter<favoriteUser, FavoriteListAdapter.MyViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<favoriteUser>() {
            override fun areItemsTheSame(oldItem: favoriteUser, newItem: favoriteUser): Boolean {
                return oldItem.username == newItem.username
            }
            override fun areContentsTheSame(oldItem: favoriteUser, newItem: favoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(val binding: ItemRowFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: favoriteUser) {
            binding.tvName.text = item.username
            Glide.with(binding.root).load(item.avatarUrl).into(binding.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        val currentNightMode = holder.itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        val iconDrawable = if (isDarkTheme) {
            R.drawable.baseline_favorite_24_dark
        } else {
            R.drawable.baseline_favorite_24
        }
        val backgroundColor = if (isDarkTheme) {
            R.color.background_black
        } else {
            R.color.white
        }
        holder.binding.btnAddFavorite.setImageResource(iconDrawable)
        holder.binding.btnAddFavorite.setBackgroundResource(backgroundColor)


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("username", item.username)
            context.startActivity(intent)
        }

        holder.binding.btnAddFavorite.setOnClickListener {
            viewModel.deleteFavorite(item.username)
        }
    }
}
