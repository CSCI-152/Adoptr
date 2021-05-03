package com.example.adoptrapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_article_display_template.*

class TemplateArticleDisplayActivity : AppCompatActivity() {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_display_template)

        val title = intent.getStringExtra("title").toString().trim()
        val authorID = intent.getStringExtra("author").toString()
        val postDate = intent.getStringExtra("postDate").toString().trim()
        val body = intent.getStringExtra("body").toString().trim()

        // getting the author information and filling the template
        firebaseFirestore.collection("users").document(authorID)
            .get(Source.SERVER)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val classObj = task.result!!.toObject<ClassUser>()
                    articleTemplateAuthor.text = classObj!!.fullName.toString().trim()

                    articleTemplateTitle.text = title
                    articleTemplateDate.text = postDate
                    articleTemplateBody.text = body
                }
                else {
                    Toast.makeText(
                        baseContext,
                        task.exception?.message.toString().trim(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } // END onCompleteListener

        val backButton = findViewById<ImageView>(R.id.article_back)
        backButton.setOnClickListener {
            finish()
        }
    } // END onCreate
}

