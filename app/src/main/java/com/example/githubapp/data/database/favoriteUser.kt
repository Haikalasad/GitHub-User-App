package com.example.githubapp.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class favoriteUser (

        @PrimaryKey(autoGenerate = false)

        @ColumnInfo(name = "username")
        var username: String = "",

        @ColumnInfo(name = "avatarUrl")
        var avatarUrl: String? = null,




    ) : Parcelable


