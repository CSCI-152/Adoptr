package com.example.adoptrapp

import java.text.SimpleDateFormat
import java.util.*

data class ClassArticle(
    val id: String = "",
    val title: String = "No title Inputted",
    val authorID: String = "",
    // saves the postDate as the current date in Year-Month-Day format
    // MM = month, mm = minutes
    val postDate: String = SimpleDateFormat("yyyy-MM-dd").format(Date()),
    val description: String = "No Description Inputted"
)