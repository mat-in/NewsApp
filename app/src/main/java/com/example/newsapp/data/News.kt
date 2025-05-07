package com.example.newsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    var title: String,
    var description: String,
    var urlToImage: String?,
    var url: String?,
    var publishedAt: String,
    var content: String
): Parcelable