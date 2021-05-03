package com.example.adoptrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_list_display_template.*
import kotlinx.android.synthetic.main.item_with_image.view.*

class ListDisplayTemplateActivity : AppCompatActivity() {

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

        firebaseFirestore
            .collection("Listings")
            //.orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documentList ->
                for (document in documentList) {
                    val postModel = document.toObject(ClassArticle::class.java)
                    if(intentValue == postModel.title){
                        displayTemplateTitle.text = postModel.title.toString()
                    }
                }
            }
    }

}