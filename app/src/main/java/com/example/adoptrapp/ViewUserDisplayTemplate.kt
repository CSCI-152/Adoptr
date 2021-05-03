package com.example.adoptrapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list_display_template.*
import kotlinx.android.synthetic.main.activity_view_user_display_template.*

class ViewUserDisplayTemplate : AppCompatActivity() {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_display_template)

        val fName = intent.getStringExtra("fullName")
        val mail = intent.getStringExtra("email")
        val status = intent.getStringExtra("role")

        //set items to layout
        fullName.text = fName
        email.text = mail
        role.text = status

        banbutton.setOnClickListener {
            banUser(fName, mail, status)
        }

        centerbutton.setOnClickListener {
            makeCenter(fName, mail, status)
        }
    }

    private fun makeCenter(fName:String?, mail:String?, status:String?) {
        TODO("Not yet implemented")
    }

    private fun banUser(fName:String?, mail:String?, status:String?) {

    }
}