package com.example.adoptrapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val buttonForgotPassword: Button = findViewById(R.id.buttonForgotPassword)
        val forgotEmailEditText: EditText = findViewById(R.id.editTextForgotEmail)

        buttonForgotPassword.setOnClickListener {
            val email: String = forgotEmailEditText.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Toast.makeText(this@ForgotPasswordActivity,
                        "Please enter an email address.",
                        Toast.LENGTH_SHORT
                ).show()
            }
            else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener{ task ->
                            // gets a task from FirebaseAuth that is stored in task
                            // outputs the error if unsuccessful
                            if (task.isSuccessful) {
                                Toast.makeText(this@ForgotPasswordActivity,
                                        "Password reset sent successfully.",
                                        Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                            else {
                                Toast.makeText(this@ForgotPasswordActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
            }
        }

    }
}