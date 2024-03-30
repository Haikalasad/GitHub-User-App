package com.example.githubapp.data.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("gists_url")
	val gistsUrl: String,

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("starred_url")
	val starredUrl: String,

	@field:SerializedName("login")
	val login: String,


	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

)
