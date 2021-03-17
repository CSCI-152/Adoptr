package com.example.adoptrapp

data class ClassTicket(
    val topic: String? = null,  // From spinner1
    val reason: String? = null, // From spinner2
    val desc: String? = null,
    val priority: Int? = null
)

// Priority is based on the topic and reason
// lowest number = higher priority