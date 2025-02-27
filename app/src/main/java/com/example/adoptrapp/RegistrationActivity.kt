package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private var bannerImage: ImageView? = null
    private var registerUser: Button? = null

    private var editTextFullName: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var editTextConfirmPassword: EditText? = null

    private var mAuth: FirebaseAuth? = null
    private val db = FirebaseFirestore.getInstance()

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
                registerAccount()
            }
        }
    }

    // returns a Boolean to use the if statement in the onClick override
    // if true it allows it to finish the activity
    // otherwise it stays in the register (this) activity
    private fun registerAccount() {
        val fullName = editTextFullName?.text.toString().trim()
        val email = editTextEmail?.text.toString().trim()
        val password = editTextPassword?.text.toString().trim()
        val confirmPassword = editTextConfirmPassword?.text.toString().trim()

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
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = FirebaseAuth.getInstance().currentUser!!.uid
                    val user = ClassUser(
                        id = uid,
                        fullName = fullName,
                        email = email
                    )
                    // Listeners put errors in the log (Logcat)
                    // the error numbers are temp to distinguish which failure was encountered
                    db.collection("users").document(uid).set(user)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                Toast.makeText(this@RegistrationActivity,
                                    "Account successfully registered!",
                                    Toast.LENGTH_LONG
                                ).show()
                                // returns the user to the main activity once the account is successfully registered
                                // this is so the sidebar gets updated
                                finish()
                                val intent = Intent(this, LandingActivity::class.java)
                                startActivity(intent)
                            }
                            else {
                                Toast.makeText(this@RegistrationActivity,
                                    task2.exception!!.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } // END onCompleteListener
                } // END if CASE
                else {
                    Toast.makeText(this@RegistrationActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_LONG
                    ).show()
                } // END else CASE
            }
        return
    }

    override fun onBackPressed() {
            finish()
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
        }
}

