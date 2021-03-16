package com.example.adoptrapp

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.adoptrapp.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private var bannerImage: ImageView? = null
    private var registerUser: Button? = null

    private var editTextFullName: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var editTextConfirmPassword: EditText? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)

        mAuth = FirebaseAuth.getInstance()

        bannerImage = findViewById(R.id.bannerImage)
        bannerImage!!.setOnClickListener(this)

        registerUser = findViewById(R.id.registerAccount)
        registerUser!!.setOnClickListener(this)

        editTextFullName = findViewById(R.id.enterFullName)
        editTextEmail = findViewById(R.id.enterEmail)
        editTextPassword = findViewById(R.id.enterPassword)
        editTextConfirmPassword = findViewById(R.id.confirmPassword)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.bannerImage -> {
                // closes this activity
                finish()
            }
            R.id.registerAccount -> {
                if(registerAccount()) {
                    finish()
                }
            }
        }
    }

    // returns a Boolean to use the if statement in the onClick override
    // if true it allows it to finish the activity
    // otherwise it stays in the register (this) activity
    private fun registerAccount(): Boolean {
        val fullName = editTextFullName?.text.toString().trim()
        val email = editTextEmail?.text.toString().trim()
        val password = editTextPassword?.text.toString().trim()
        val confirmPassword = editTextConfirmPassword?.text.toString().trim()

        if (fullName.isEmpty()){
            // Checks if the email box is filled out
            editTextFullName?.error = "An email is required."
            return false
        }

        if (email.isEmpty()){
            // Checks if the email box is filled out
            editTextEmail?.error = "An email is required."
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // Checks if the email has a valid format
            editTextEmail?.error = "Please provide a valid email."
            editTextEmail?.requestFocus()
            return false
        }

        if (password.isEmpty()){
            // Checks if the password box is filled out
            editTextPassword?.error = "A password is required."
            editTextPassword?.requestFocus()
            return false
        }
        if (confirmPassword.isEmpty()){
            // Checks if the confirm password box is filled out
            editTextConfirmPassword?.error = "Please confirm your password."
            editTextConfirmPassword?.requestFocus()
            return false
        }

        if (password != confirmPassword){
            // checks if the passwords match
            editTextConfirmPassword?.error = "Passwords do not match."
            editTextConfirmPassword?.requestFocus()
            return false
        }

        if (password.length < 6){
            // Checks if the password is at least 6 characters long
            editTextPassword?.error = "Password must be at least 6 characters long"
            editTextPassword?.requestFocus()
            return false
        }

        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful) {
                        val user = User(fullName = fullName, email = email)

                        // the error numbers are temp to distinguish which failure was encountered
                        FirebaseAuth.getInstance().currentUser?.let {
                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(it.uid)
                                .setValue(user).addOnCompleteListener(object: OnCompleteListener<Void>{
                                    override fun onComplete(p0: Task<Void>) = if (p0.isSuccessful) {
                                        Toast.makeText(this@RegistrationActivity, "Account successfully registered!", Toast.LENGTH_LONG).show()
                                    }
                                    else {
                                        Toast.makeText(this@RegistrationActivity, "Account registration failed. ERROR: 01", Toast.LENGTH_LONG).show()
                                    }

                                })
                        }
                    }
                    else {
                        Toast.makeText(this@RegistrationActivity, "Account registration failed. ERROR: 02", Toast.LENGTH_LONG).show()
                    }
                }
            })
        return true
    }
}
