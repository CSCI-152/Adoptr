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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
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
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // converts the grabbed document to ClassUser object class and takes the petMarks List
                        val classObj = task.result!!.toObject<ClassUser>()
                        petMarksList = classObj?.petMarks
                        val titleList = mutableListOf<String>()

                        if (!petMarksList.isNullOrEmpty()) {
                            for (i in petMarksList!!) {
                                db.collection("Listings").document(i).get()
                                    .addOnCompleteListener { task2 ->
                                        if(task2.isSuccessful) {
                                            val classObj2 = task2.result!!.toObject<PostModel>()
                                            val newTitle = classObj2!!.title.toString().trim() + "-" + classObj2.id.toString().trim()
                                            titleList.add(newTitle)
                                            val arrayAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, titleList)
                                            listView = view.findViewById(R.id.profilePetMarks)

                                            listView.setOnItemClickListener { parent, view, position, id ->
                                                val clickedItem = parent.getItemAtPosition(position) // clicked item
                                                val splitString = clickedItem.toString().split("-")
                                                val clickedID = splitString.last()
                                                db.collection("Listings").document(clickedID).get()
                                                    .addOnCompleteListener { task3 ->
                                                        if (task3.isSuccessful) {
                                                            val classObj3 = task3.result!!.toObject<PostModel>()
                                                            val bundle = bundleOf(
                                                                "pid" to classObj3!!.id,
                                                                "title" to classObj3.title,
                                                                "author" to classObj3.author,
                                                                "url" to classObj3.url,
                                                                "desc" to classObj3.description
                                                            )
                                                            val intent = Intent(this.context, TemplateListDisplayActivity::class.java)
                                                            intent.putExtras(bundle)
                                                            startActivity(intent)
                                                        }
                                                    }

                                            }

                                            listView.adapter = arrayAdapter
                                        }
                                    } // END addOnCompleteListener
                            }
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

} // END fragment CLASS

