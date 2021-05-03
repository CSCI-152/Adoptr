package com.example.adoptrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_view_report_display_template.*
import kotlinx.android.synthetic.main.activity_view_user_display_template.*

class ViewReportDisplayTemplate : AppCompatActivity() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_report_display_template)

        val topic = intent.getStringExtra("topic")
        val reason = intent.getStringExtra("reason")
        val desc = intent.getStringExtra("desc")

        //set items to layout
        topicText.text = topic
        reasonText.text = reason
        descText.text = desc
        
        deletePostButton.setOnClickListener { 
            deletePost(topic, reason, desc)
        }
    }

    private fun deletePost(topic: String?, reason: String?, desc: String?) {
        db.collection("supportTickets")
            .document().get()
            .addOnSuccessListener { document ->
                if (document != null){

                }
        }
    }
}