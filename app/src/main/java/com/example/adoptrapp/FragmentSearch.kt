package com.example.adoptrapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_search.*

class FragmentSearch: Fragment() {

    companion object {
        // provide a new instance of the HomeFragment
        fun newInstance() : FragmentSearch {
            return FragmentSearch()
        }
    }

    private val db = FirebaseFirestore.getInstance()

    private lateinit var searchButton: Button
    private lateinit var spinnerAnimal: Spinner
    private lateinit var spinnerAge: Spinner
    private lateinit var spinnerGender: Spinner

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        // code that needs to be run when the fragment starts up goes here

        // Setting all the values of the global variables
        searchButton = view.findViewById(R.id.searchButton)
        spinnerAnimal = view.findViewById(R.id.spinnerAnimal)
        spinnerAge = view.findViewById(R.id.spinnerAge)
        spinnerGender = view.findViewById(R.id.spinnerGender)

        searchButton.setOnClickListener{
            submitSearch(view)
        }

        return view
    }

    private fun submitSearch(view: View){

        val animalSelected = spinnerAnimal.selectedItem.toString().trim()
        val ageSelected = spinnerAge.selectedItem.toString().trim()
        val genderSelected = spinnerGender.selectedItem.toString().trim()

        if (animalSelected.isEmpty() || ageSelected.isEmpty() || genderSelected.isEmpty()) {
            Toast.makeText(
                view.context,
                "Please select items from the spinners.",
                Toast.LENGTH_LONG
            ).show()
        }
        else {

            val bundle = bundleOf(
                "tag1" to animalSelected,
                "tag2" to ageSelected,
                "tag3" to genderSelected
            )

            val homeFragment = FragmentHome()
            homeFragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit()
        }
    } // END submitSearch
} // END class