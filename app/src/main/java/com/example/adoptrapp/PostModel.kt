package com.example.adoptrapp

import android.net.Uri

data class PostModel(
    val id: String? = "",
    val author: String? ="",
    val title: String? = "",
    val description: String? = "",
    val date: String? = "",
    val image: String? = "",
    val url: String? ="",
    val tag1: String? ="",
    val tag2: String? ="",
    val tag3: String? =""
)