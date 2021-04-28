package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FragmentProfile : Fragment() {

    private lateinit var listView: ListView

    companion object {
        // provide a new instance of the HomeFragment
        fun newInstance() : FragmentProfile {
            return FragmentProfile()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val forgotPassButton = view.findViewById<Button>(R.id.profileChangePassButton)
        forgotPassButton.setOnClickListener {
            val intent = Intent(activity, ForgotPasswordActivity::class.java)
            startActivity(intent)   // sends to the new intent
        }

        val tempPetMarkButton = view.findViewById<Button>(R.id.profileTempButton)
        tempPetMarkButton.setOnClickListener {
            addToPetMarks()
        }

        displayPetMarks(view)
        return view
    }


    private fun displayPetMarks(view: View) {
        var petMarksList: List<String>? = emptyList()   // emptyList to not error out when returning
        if (Firebase.auth.currentUser != null) {
            // Initialize variables/values
            val db = FirebaseFirestore.getInstance() // make a connection to the database
            val uid = Firebase.auth.currentUser!!.uid
            // Fetch the petMarks list from db
            val docRef = db.collection("users").document(uid)
            docRef.get(Source.SERVER)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        // converts the grabbed document to ClassUser object class and takes the petMarks List
                        val classObj = task.result!!.toObject<ClassUser>()
                        petMarksList = classObj?.petMarks
                        if (!petMarksList.isNullOrEmpty()) {
                            // not empty or null
                            val mutablePetMarksList = petMarksList!!.toMutableList()
                            val arrayAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, mutablePetMarksList)
                            listView = view.findViewById(R.id.profilePetMarks)
                            listView.adapter = arrayAdapter

                        } // END if CASE
                        else {
                            makeText(
                                this.activity,
                                "Acquired list is either null or empty",
                                Toast.LENGTH_LONG
                            ).show()
                        } // END else CASE
                    } // END if CASE
                    else {
                        makeText(
                            this.context,
                            task.exception?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    } // END else CASE
                }
        } // END if CASE
        else {
            // Display a Toast telling the user to sign in or sign up
            makeText(this.context,
                "Please sign in or register an account for the side menu.",
                Toast.LENGTH_LONG
            ).show()
        } // END else CASE
    } // END displayPetMarks CASE


    // This function should be added to the listing template page
    private fun addToPetMarks() {
        val listingID = "asdasd1"   // temporary id of the listing; will be fetched using item id of current view
                                    // function should be something like the comment below
                                    // val listingID = view.findViewByID<TextBox>(R.id.idOfTextBox)
        if (Firebase.auth.currentUser != null) {
            // The user is logged in
            val db = FirebaseFirestore.getInstance() // make a connection to the database
            val uid = Firebase.auth.currentUser!!.uid
            // val data = hashMapOf( "listingID" to listingID )
            db.collection("users").document(uid)
                .update("petMarks", FieldValue.arrayUnion(listingID)) //add its to the petMark array
                .addOnSuccessListener {
                    makeText(this.context,
                        "Account successfully registered!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    makeText(this.context,
                        e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        } // END if CASE
        else {
            // Display a Toast telling the user to sign in or sign up
            makeText(this.context,
                "Please sign in or register an account for the side menu.",
                Toast.LENGTH_LONG
            ).show()
        } // END else CASE
    } // END addToPetMarks FUNCTION

} // END fragment CLASS

