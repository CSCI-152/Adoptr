package com.example.adoptrapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SupportTicketActivity : AppCompatActivity(), View.OnClickListener {

    // private var backButton: Button? = null // need to add a button to exit this activity

    private val db = FirebaseFirestore.getInstance()

    private lateinit var submitButton: Button
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var desc: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.support_ticket_activity)

        submitButton = findViewById(R.id.submitTicketButton)
        submitButton.setOnClickListener(this)

        spinner1 = findViewById(R.id.spinnerMenu1)
        spinner2 = findViewById(R.id.spinnerMenu2)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.submitTicketButton -> {
                if(submitTicket()) {
                    finish()
                }
            }
        }
    }

    private fun submitTicket(): Boolean {
        val spinnerEntry1 = spinner1.selectedItem.toString().trim()
        val spinnerEntry2 = spinner2.selectedItem.toString().trim()
        val descView = findViewById<EditText>(R.id.editTextDescriptionBox)

        val topic = spinnerEntry1.trim()
        val reason = spinnerEntry2.trim()
        desc = descView.text.toString()

        if (topic.isEmpty()) {
            // Checks if the first spinner drop down is filled out
            Toast.makeText(this.baseContext,"Please selected an item from the dropdown1", Toast.LENGTH_SHORT).show()
            return false
        }

        if (reason.isEmpty()) {
            // Checks if the second spinner drop down is filled out
            Toast.makeText(this.baseContext,"Please selected an item from the dropdown2", Toast.LENGTH_SHORT).show()
            return false
        }

        if (desc == ""){
            // Checks if the email has a valid format
            descView?.error = "Please provide a short description of the issue."
            descView?.requestFocus()
            return false
        }

        // Calculating the weights for the issues
        // Priority Weights
        // App = 1, Shelter = 2, User = 3, Account = 4, Other = 5
        // Bug = 1, Interaction = 2, Harassment = 3, General = 4,  Other = 5

        var priorityOfTopic = 0
        var priorityOfReason = 0
        // getting the priority value of the topic
        when (topic){
            "App" -> {
                priorityOfTopic = 1
            }
            "Shelter" -> {
                priorityOfTopic = 2
            }
            "User" -> {
                priorityOfTopic = 3
            }
            "Account" -> {
                priorityOfTopic = 4
            }
            "Other" -> {
                priorityOfTopic = 5
            }
        }

        // getting the priority value of the reason
        when (reason){
            "Bug" -> {
                priorityOfReason = 1
            }
            "Interaction" -> {
                priorityOfReason = 2
            }
            "Harassment" -> {
                priorityOfReason = 3
            }
            "General" -> {
                priorityOfReason = 4
            }
            "Other" -> {
                priorityOfReason = 5
            }
        }

        // highest priority should be 2
        val sumPriority = priorityOfTopic + priorityOfReason
        // SEND THE DATA TO FIRESTORE
        val data = ClassTicket(topic, reason, desc, sumPriority)
        // add() makes a auto generated id
        db.collection("supportTickets")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        // Toast to show this function works
        Toast.makeText(this.baseContext,"Ticket Submitted", Toast.LENGTH_SHORT).show()
        return true
    }
}