package com.example.adoptrapp

class User() {
    var fullName: String? = null
    var email: String? = null

    constructor(fullName: String, email: String?) : this() {
        this.fullName = fullName
        this.email = email
    }
}

