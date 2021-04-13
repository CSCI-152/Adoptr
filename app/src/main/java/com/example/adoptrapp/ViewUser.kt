package com.example.adoptrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class ViewUser : AppCompatActivity() {

    private lateinit var displayResult2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_user_activity)

        displayResult2 = findViewById(R.id.view_user)
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()
                for (document in it.result!!){
                    result.append(document.data.getValue("role")).append("\n")
                }
                displayResult2.setText(result)
            }
    }
}