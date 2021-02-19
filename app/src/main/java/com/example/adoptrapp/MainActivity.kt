package com.example.adoptrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //create the floating toolbar

        val button = findViewById<Button>(R.id.toFirstFrag_button)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}