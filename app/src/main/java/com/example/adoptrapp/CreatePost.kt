package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


abstract class CreatePost : AppCompatActivity(), View.OnClickListener {


    private var petUpload: ImageView? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)


        petUpload = findViewById<ImageView>(R.id.petUpload)
        petUpload!!.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.petUpload -> {
                val intent = Intent(this, CreatePost::class.java)
                startActivity(intent)






            }



        }
    }

}

