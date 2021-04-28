package com.example.adoptrapp

import com.google.firebase.Timestamp

data class PostModel(
    val author: String? ="",
    val title: String? = "",
    val description: String? = "",
    val date: String? = "",
    val image: String? = "",
    val tag1: String? ="",
    val tag2: String? ="",
    val tag3: String? =""
)