package com.example.adoptrapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class FirebaseRepo {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFireStore: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUser(): FirebaseUser{
        return firebaseAuth.currentUser
    }

}