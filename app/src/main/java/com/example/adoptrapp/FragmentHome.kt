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
    private val recycleViewAdapter : RecycleViewAdapterPost = RecycleViewAdapterPost(postList,this)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        // Write the code here as you would with LandingActivity

        if (arguments == null){
            loadData(0)
        }
        else {
            loadData(1)
        }

        val firestoreList = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.firestore_list)
        firestoreList.layoutManager = LinearLayoutManager(this.context)
        firestoreList.adapter = recycleViewAdapter

        return view
    }

    private fun loadData(control: Int){
        when (control){
            1 -> {
                // if control = 1 use search load
                val tag1 = requireArguments().getString("tag1").toString().trim()
                val tag2 = requireArguments().getString("tag2").toString().trim()
                val tag3 = requireArguments().getString("tag3").toString().trim()

                getSearchList(tag1, tag2, tag3).addOnCompleteListener{
                    if(it.isSuccessful){
                        if (it.result!!.isEmpty) {
                            Toast.makeText(this.context,"No Results!", Toast.LENGTH_LONG).show()
                        }
                        else {
                            postList = it.result!!.toObjects(PostModel::class.java)
                            recycleViewAdapter.postListItem = postList
                            recycleViewAdapter.notifyDataSetChanged()
                        }
                    }
                    else{
                        Toast.makeText(this.context,"Failed to retrieve data", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else -> {
                // otherwise use default load
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
        }
    } // END loadData FUNC

    private fun getPostList(): Task<QuerySnapshot> {
        return firebaseFirestore
            .collection("Listings")
            //.orderBy("date", Query.Direction.DESCENDING)
            .get()
    }

    private fun getSearchList(tag1: String, tag2: String, tag3: String) : Task<QuerySnapshot> {
        return firebaseFirestore
            .collection("Listings")
            .whereEqualTo("tag1", tag1).whereEqualTo("tag2", tag2).whereEqualTo("tag3", tag3).get()
    }

    //passing values onclick
    override fun invoke(postModel: PostModel) {
        val bundle = bundleOf(
            "pid" to postModel.id,
            "title" to postModel.title,
            "author" to postModel.author,
            "url" to postModel.url,
            "desc" to postModel.description
        )
        val i = Intent(this.context, TemplateListDisplayActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}