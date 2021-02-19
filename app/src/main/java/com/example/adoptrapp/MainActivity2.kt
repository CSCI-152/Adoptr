package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.adoptrapp.MainActivity2 as MainActivity21
import com.example.adoptrapp.User as User


class MainActivity2 : AppCompatActivity(), View.OnClickListener {

    private var bannerImage: ImageView? = null
    private var registerUser: Button? = null

    private var editTextFullName: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var editTextConfirmPassword: EditText? = null


    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mAuth = FirebaseAuth.getInstance()

        bannerImage = findViewById<ImageView>(R.id.bannerImage)
        bannerImage!!.setOnClickListener(this)

        registerUser = findViewById<Button>(R.id.registerAccount)
        registerUser!!.setOnClickListener(this)

        editTextFullName = findViewById<EditText>(R.id.enterFullName)
        editTextEmail = findViewById<EditText>(R.id.enterEmail)
        editTextPassword = findViewById<EditText>(R.id.enterPassword)
        editTextConfirmPassword = findViewById<EditText>(R.id.confirmPassword)
    }

    override fun onClick(v: View?) {
            when (v?.id){
                R.id.bannerImage -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.registerAccount -> {
                    registerAccount()
                }
            }
    }

    private fun registerAccount() {
        var fullName: String = editTextFullName?.text.toString().trim()
        var email: String = editTextEmail?.text.toString().trim()
        var password: String = editTextPassword?.text.toString().trim()
        var confirmPassword: String = editTextConfirmPassword?.text.toString().trim()

        if (fullName.isEmpty()){
            // Checks if the email box is filled out
            editTextFullName?.error = "An email is required."
            return
        }

        if (email.isEmpty()){
            // Checks if the email box is filled out
            editTextEmail?.error = "An email is required."
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // Checks if the email has a valid format
            editTextEmail?.error = "Please provide a valid email."
            editTextEmail?.requestFocus()
            return
        }

        if (password.isEmpty()){
            // Checks if the password box is filled out
            editTextPassword?.error = "A password is required."
            editTextPassword?.requestFocus()
            return
        }
        if (confirmPassword.isEmpty()){
            // Checks if the confirm password box is filled out
            editTextConfirmPassword?.error = "Please confirm your password."
            editTextConfirmPassword?.requestFocus()
            return
        }

        if (password != confirmPassword){
            // checks if the passwords match
            editTextConfirmPassword?.error = "Passwords do not match."
            editTextConfirmPassword?.requestFocus()
            return
        }

        if (password.length < 6){
            // Checks if the password is at least 6 characters long
            editTextPassword?.error = "Password must be at least 6 characters long"
            editTextPassword?.requestFocus()
            return
        }

        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful) {
                        val user = User(fullName = fullName, email = email)

                        FirebaseAuth.getInstance().currentUser?.let {
                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(it.uid)
                                .setValue(user).addOnCompleteListener(object: OnCompleteListener<Void>{
                                    override fun onComplete(p0: Task<Void>) {
                                        if (p0.isSuccessful) {
                                            Toast.makeText(this@MainActivity2, "Account successfully registered!", Toast.LENGTH_LONG).show()
                                        }
                                        else {
                                            Toast.makeText(this@MainActivity2, "Account registration failed.", Toast.LENGTH_LONG).show()
                                        }
                                    }

                                })
                        }
                    }
                    else {
                        Toast.makeText(this@MainActivity2, "Account registration failed.", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}
