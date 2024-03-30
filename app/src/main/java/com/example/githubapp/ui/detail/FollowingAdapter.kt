package com.example.githubapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.response.ListFollowingResponse
import com.example.githubapp.databinding.ItemRowBinding

class FollowingAdapter : ListAdapter<ListFollowingResponse,FollowingAdapter.MyViewHolder>(diffCallback)

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FollowingAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class MyViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListFollowingResponse) {
            binding.tvName.text = item.login
            Glide.with(binding.root.context).load(item.avatarUrl).into(binding.imgItemPhoto)
        }
    }
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ListFollowingResponse>() {
            override fun areItemsTheSame(oldItem: ListFollowingResponse, newItem: ListFollowingResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListFollowingResponse, newItem: ListFollowingResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

}



