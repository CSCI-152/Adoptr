package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentProfile : Fragment() {

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
        forgotPassButton.setOnClickListener(){

            val intent = Intent(activity, ForgotPasswordActivity::class.java)
            startActivity(intent)   // sends to the new intent
        }


        return view
    }

    // This function should be added to the listing
    private fun addToPetMarks(){

    }


}