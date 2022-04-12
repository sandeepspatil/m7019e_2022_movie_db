package com.ltu.m7019edemoapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey()
    var id: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "poster_path")
    var posterPath: String,

    @ColumnInfo(name = "banner_path")
    var bannerPath: String,

    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "overview")
    var overview: String
) : Parcelable