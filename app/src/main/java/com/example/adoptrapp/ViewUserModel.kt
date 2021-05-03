package com.example.adoptrapp

data class ViewUserModel(
    val id: String = "",
    var fullName: String? = null,
    var email: String? = null,
    var role: String? = "user",
    var petMarks: List<String>? = null
)