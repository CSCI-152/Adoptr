package com.example.adoptrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class ViewReport : AppCompatActivity() {

    private lateinit var displayResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_report_activity)

        displayResult = findViewById(R.id.view_report_result)
        val db = FirebaseFirestore.getInstance()

        db.collection("supportTickets")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()
                for (document in it.result!!){
                    result.append(document.data.getValue("desc")).append("\n")
                }
                displayResult.setText(result)
            }

    }

}