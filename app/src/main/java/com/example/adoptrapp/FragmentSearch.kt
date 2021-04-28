package com.example.adoptrapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentSearch: Fragment() {

    companion object {
        // provide a new instance of the HomeFragment
        fun newInstance() : FragmentSearch {
            return FragmentSearch()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        // code that needs to be run when the fragment starts up goes here




        return view
    }
}