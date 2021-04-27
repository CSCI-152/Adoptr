package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

open class RecycleViewActivity : AppCompatActivity(), (PostModel) -> Unit {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var postList: List<PostModel> = ArrayList()
    private val recycleViewAdapter : RecycleViewAdapterPost = RecycleViewAdapterPost(postList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        loadData()

        val firestoreList = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.firestore_list)
        firestoreList.layoutManager = LinearLayoutManager(this)
        firestoreList.adapter = recycleViewAdapter
    }

    private fun loadData(){
        getPostList().addOnCompleteListener{
            if(it.isSuccessful){
                postList = it.result!!.toObjects(PostModel::class.java)
                recycleViewAdapter.postListItem = postList
                recycleViewAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(applicationContext,"Failed to retrieve data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getPostList(): Task<QuerySnapshot>{
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
        val i = Intent(this, ListDisplayTemplateActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}