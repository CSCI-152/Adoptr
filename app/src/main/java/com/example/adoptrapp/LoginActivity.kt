package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var bannerImage: ImageView? = null
    private var loginButton: Button? = null
    private var editLoginEmail: EditText? = null
    private var editLoginPassword: EditText? = null

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        bannerImage = findViewById<ImageView>(R.id.bannerImage)

        loginButton = findViewById<Button>(R.id.loginButton) as Button

        editLoginEmail = findViewById<EditText>(R.id.loginEmail)
        editLoginPassword = findViewById<EditText>(R.id.loginPassword)


        loginButton!!.setOnClickListener(View.OnClickListener {
            val email = editLoginEmail!!.text.toString().trim()
            val password = editLoginPassword!!.text.toString().trim()

            if (email.isEmpty()){
                Toast.makeText(applicationContext,"Please Enter your email.",Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                // Checks if the email has a valid format
                editLoginEmail?.error = "Email is not valid."
                editLoginEmail?.requestFocus()
                return@OnClickListener
            }

            auth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener {
                        task ->

                    if (!task.isSuccessful){
                        Toast.makeText(this@LoginActivity, "Login Failed.",Toast.LENGTH_LONG).show()
                    }
                    else {
                        finish()
                        startActivity(Intent(this@LoginActivity,LandingActivity::class.java))
                    }
                })
        })
    }
}