package com.example.githubapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.response.Follower
import com.example.githubapp.databinding.ItemRowBinding

class FollowerAdapter : ListAdapter<Follower, FollowerAdapter.MyViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Follower>() {
            override fun areItemsTheSame(oldItem: Follower, newItem: Follower): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Follower, newItem: Follower): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    inner class MyViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Follower) {
            binding.tvName.text = item.login
            Glide.with(binding.root.context).load(item.avatarUrl).into(binding.imgItemPhoto)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
