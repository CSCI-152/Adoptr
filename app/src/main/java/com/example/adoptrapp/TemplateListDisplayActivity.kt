package com.example.adoptrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_list_display_template.*
import kotlinx.android.synthetic.main.create_post_activity.view.*
import kotlinx.android.synthetic.main.item_with_image.view.*
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.Toast.makeText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase

class TemplateListDisplayActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_display_template)

        displayTemplateTitle.text = intent.getStringExtra("title")
        displayDesc.text = intent.getStringExtra("desc")
        val url= intent.extras!!.get("url").toString()
        likeButton.setOnClickListener {
            addToPetMarks(url)
        }

        Glide.with(this.baseContext).load(url).into(displayTemplateImage)

        val sid: String = intent.getStringExtra("author")!!

        val docRef = db.collection("users").document(sid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    userName.text = document.getString("fullName")
                    userEmail.text = document.getString("email")
                } else {
                }
            }
    }

    private fun addToPetMarks(p1: Any) {
        if (Firebase.auth.currentUser != null) {
            // The user is logged in
            val db = FirebaseFirestore.getInstance() // make a connection to the database
            val uid = Firebase.auth.currentUser!!.uid
            // val data = hashMapOf( "listingID" to listingID )
            db.collection("users").document(uid)
                .update("petMarks", FieldValue.arrayUnion(p1)) //add its to the petMark array
                .addOnSuccessListener {
                    makeText(this,
                        "Account successfully registered!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    makeText(this,
                        e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        } // END if CASE
        else {
            // Display a Toast telling the user to sign in or sign up
            makeText(this,
                "Please sign in or register an account for the side menu.",
                Toast.LENGTH_LONG
            ).show()
        } // END else CASE
    } // END addToPetMarks FUNCTION

}
