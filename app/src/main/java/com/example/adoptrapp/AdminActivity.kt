package com.example.adoptrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity)

        val userButton = findViewById<Button>(R.id.view_user)
        userButton.setOnClickListener{
            viewUser()
        }


        val reportButton = findViewById<Button>(R.id.view_report)
        reportButton.setOnClickListener{
            viewReport()
        }
    } // END onCreate

    private fun viewUser() {
        val intent = Intent(this, ViewUser::class.java)
        // finish()
        startActivity(intent)
    } // END viewUser

    private fun viewReport() {
        val intent = Intent(this, ViewReport::class.java)
        // finish()
        startActivity(intent)
    } // END viewReport
} // END Class