package com.example.adoptrapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list_display_template.*

class TemplateListDisplayActivity : AppCompatActivity() {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_display_template)

        val intentValue = intent.getStringExtra("title")

        //val intentValue = intent.getStringExtra("title") //this for image

        firebaseFirestore
            .collection("Listings")
            //.orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documentList ->
                for (document in documentList) {
                    val postModel = document.toObject(PostModel::class.java)
                    if(intentValue == postModel.title){
                        displayTemplateTitle.text = postModel.title.toString()
                    }
                }
            }
    }

}