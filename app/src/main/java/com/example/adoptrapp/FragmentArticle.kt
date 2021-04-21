package com.example.adoptrapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentArticle: Fragment() {

    companion object {
        // provide a new instance of the HomeFragment
        fun newInstance() : FragmentArticle {
            return FragmentArticle()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }
}