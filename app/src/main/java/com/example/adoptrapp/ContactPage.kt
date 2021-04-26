package com.example.adoptrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Tag
import com.google.firebase.firestore.FirebaseFirestore

class ContactPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_page)

        //back button not needed as for now, android has default back button
        //create action bar
        //val actionbar = supportActionBar
        //title of page in action bar
        //supportActionBar!!.title = "Contact Page"
        //sets action bar to top of screen
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //add button to go to page to send a text message to adoption shelter
        val messagebtn = findViewById<Button>(R.id.messageButton)

        messagebtn.setOnClickListener {
            val intent = Intent(this, messager::class.java)
            startActivity(intent)
        }

        // connect to database
        val db = FirebaseFirestore.getInstance()

        val name = findViewById<TextView>(R.id.name)
        val phone = findViewById<TextView>(R.id.phone)

        //grab the value sent from the Adoption Shelter Listing template page
        //val shelterName = intent.getStringExtra('Username')

        val docRef = db.collection("users").document("E7IO3OiDBXtoYA0Hzbio")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    name.text = document.getString("name")
                    phone.text = document.getString("phone")
                } else {
                }
            }
    }

    // go to previous activity function
   // override fun onSupportNavigateUp(): Boolean {
    //    onBackPressed()
     //   return true
   // }
}
