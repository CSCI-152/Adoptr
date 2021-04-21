package com.example.adoptrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Admin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity)

    }

    fun view_report(view: View) {
        val intent = Intent(this, ViewReport::class.java)
        startActivity(intent)
    }
    fun view_user(view: View) {
        val intent = Intent(this, ViewUser::class.java)
        startActivity(intent)
    }
}