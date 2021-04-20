package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import io.grpc.InternalChannelz.id

class UserProfileActivity : AppCompatActivity(), View.OnClickListener  {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        // need to implement the petmark system
        val buttonSendToPasswordReset: Button = findViewById(R.id.profileChangePassButton)
        buttonSendToPasswordReset.setOnClickListener() {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            finish()                // closes the current intent
            startActivity(intent)   // sends to the new intent
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.profileChangePassButton -> {
                // Sends the user to the change password page
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                finish()                // closes the current intent
                startActivity(intent)   // sends to the new intent
            }
        }
    }
}