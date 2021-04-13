package com.example.adoptrapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class UserProfileActivity : AppCompatActivity(), View.OnClickListener  {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)


    }


    override fun onClick(v: View?) {
        when (v?.id){
            R.id.profileChangeNameButton -> {
                // makes a prompt popup with current user name then saves as
                // the submitted name as the new name


            }
            R.id.profileChangePassButton -> {

            }
        }
    }

    private fun updateName(): Boolean {
        if (FirebaseAuth.getInstance().currentUser != null){
            val user = db.collection("users")
                    .document(FirebaseAuth.getInstance().currentUser.uid).get().addOnSuccessListener {
                        documentSnapshot ->
                        findViewById<EditText>(R.id.currentUserEmail).text = (documentSnapshot.toObject<ClassUser>()).fullName
                    }
            return true
        }
        // if the user isn't signed in return a failed signal
        return false
    }

    private fun changePassword(): Boolean{
        if (FirebaseAuth.getInstance().currentUser != null) {
            AuthCredential cred = email
        }
    }
}