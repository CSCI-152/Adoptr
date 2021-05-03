package com.example.adoptrapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CreateArticleActivity : AppCompatActivity(), View.OnClickListener {

    // initializing global variables
    private var submitButton: Button? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_article)

        submitButton = findViewById<Button>(R.id.submitArticleButton)
        submitButton!!.setOnClickListener(this)

    } // EMD onCreate FUNC

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.submitArticleButton -> {
                if(submitArticle()) {
                    finish()
                }
            }
        }
    } // END onClick FUNC

    private fun submitArticle() : Boolean {

        val editTextTitle = findViewById<EditText>(R.id.editTextArticleTitle)
        val editTextBody = findViewById<EditText>(R.id.editTextArticleBody)

        if (editTextTitle.text.isNullOrEmpty()){
            // is Null or Empty
            Toast.makeText(
                this.baseContext,
                "Please enter a title.",
                Toast.LENGTH_LONG)
                .show()
            return false
        }

        if (editTextBody.text.isNullOrEmpty()) {
            Toast.makeText(
                this.baseContext,
                "Please enter a Article Body.",
                Toast.LENGTH_LONG)
                .show()
            return false
        }
        val title = editTextTitle.text.toString().trim()
        val body = editTextBody.text.toString().trim()
        val uid = Firebase.auth.currentUser!!.uid

        val newDocRef = db.collection("articles").document()

        val data = ClassArticle(
            id = newDocRef.id,
            title = title,
            authorID = uid,
            // postDate is set by the class
            description = body
        )
        newDocRef.set(data)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${newDocRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }

        Toast.makeText(
            this.baseContext,
            "Article Submitted",
            Toast.LENGTH_LONG
        ).show()
        return true
    } // END submitArticle FUNC
} // END CLASS