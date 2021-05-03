package com.example.adoptrapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_view_user_display_template.*

class ViewUserDisplayTemplate : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_display_template)

        val fName = intent.getStringExtra("fullName")
        val mail = intent.getStringExtra("email")
        val status = intent.getStringExtra("role")
        val id = intent.getStringExtra("id")

        //set items to layout
        fullName.text = fName
        email.text = mail
        role.text = status

        banbutton.setOnClickListener {
            banUser(id)
        }

        centerbutton.setOnClickListener {
            makeCenter(id)
        }
    }

    private fun makeCenter(id:String?) {
        if(id != null){
            db.collection("users")
                .document(id)
                .update("role", "center")
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "User become Center", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun banUser(id:String?) {
        if (id != null) {
            db.collection("users")
                .document(id)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "User Banned", Toast.LENGTH_LONG).show()
                }
        }
    }
}