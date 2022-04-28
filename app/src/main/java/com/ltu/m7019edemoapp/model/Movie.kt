package com.ltu.m7019edemoapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey()
    @Json(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title: String,

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    var posterPath: String,

    @ColumnInfo(name = "banner_path")
    @Json(name = "backdrop_path")
    var bannerPath: String?,

    @ColumnInfo(name = "release_date")
    @Json(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "overview")
    @Json(name = "overview")
    var overview: String
) : Parcelable