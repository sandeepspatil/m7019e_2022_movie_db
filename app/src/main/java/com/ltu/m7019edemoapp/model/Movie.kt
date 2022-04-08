package com.ltu.m7019edemoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val posterPath: String,
    val bannerPath: String,
    val releaseDate: String,
    val overview: String
) : Parcelable