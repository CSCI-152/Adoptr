package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FragmentHome: Fragment(), (PostModel) -> Unit {

    companion object {
        // provide a new instance of the HomeFragment
        fun newInstance() : FragmentHome {
            return FragmentHome()
        }
    }

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var postList: List<PostModel> = ArrayList()
    private val recycleViewAdapter : RecycleViewAdapter = RecycleViewAdapter(postList,this)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        // Write the code here as you would with LandingActivity
        loadData()
        val firestoreList = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.firestore_list)
        firestoreList.layoutManager = LinearLayoutManager(this.context)
        firestoreList.adapter = recycleViewAdapter

        return view
    }

    private fun loadData(){
        getPostList().addOnCompleteListener{
            if(it.isSuccessful){
                postList = it.result!!.toObjects(PostModel::class.java)
                recycleViewAdapter.postListItem = postList
                recycleViewAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(this.context,"Failed to retrieve data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getPostList(): Task<QuerySnapshot> {
        return firebaseFirestore
            .collection("Listings")
            //.orderBy("date", Query.Direction.DESCENDING)
            .get()
    }

    //passing values onclick
    override fun invoke(postModel: PostModel) {
        val title = postModel.title.toString()
        var bundle = bundleOf(
            "title" to title
        )
        val i = Intent(this.context, ListDisplayTemplateActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}