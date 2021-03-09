package com.example.adoptrapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SupportTicketActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.support_ticket_activity)

        val spinner1 = findViewById<Spinner>(R.id.spinnerMenu1)
        val spinner2 = findViewById<Spinner>(R.id.spinnerMenu2)
        val list1 = arrayOf("Account", "App", "Shelter", "User", "Other")
        val list2 = arrayOf("Bug", "Interaction", "Harassment", "General Support", "Other")


        // need to make is so the initial item is a non-selectable item
        // Populating spinner1 with list1
        ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list1)
                .also{ adapter1 ->
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner1.adapter = adapter1
                }

        // Populating spinner2 with list2
        ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list2)
                .also{ adapter2 ->
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner2.adapter = adapter2
                }
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
        val spinnerEntry1 = findViewById<Spinner>(R.id.spinnerMenu1).selectedItem
        val spinnerEntry2 = findViewById<Spinner>(R.id.spinnerMenu2).selectedItem
        val editTextDesc = findViewById<EditText>(R.id.editTextDescriptionBox)

        val entry1 = spinnerEntry1?.toString()?.trim()
        val entry2 = spinnerEntry2?.toString()?.trim()
        val desc = editTextDesc?.text.toString().trim()

        if (entry1!!.isEmpty()){
            // Checks if the first spinner drop down is filled out
            Toast.makeText(this.baseContext,"Please selected an item from the dropdown", Toast.LENGTH_SHORT).show()
            return false
        }

        if (entry2!!.isEmpty()){
            // Checks if the second spinner drop down is filled out
            Toast.makeText(this.baseContext,"Please selected an item from the dropdown", Toast.LENGTH_SHORT).show()
            return false
        }

        if (desc.isEmpty()){
            // Checks if the email has a valid format
            editTextDesc?.error = "Please provide a short description of the issue."
            editTextDesc?.requestFocus()
            return false
        }

        // SEND THE DATA TO FIRESTORE

        // Toast to show this function works
        Toast.makeText(this.baseContext,"Ticket Submitted", Toast.LENGTH_SHORT).show()
        return true
    }

}