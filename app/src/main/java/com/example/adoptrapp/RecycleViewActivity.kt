package com.example.adoptrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_recycle_view.*

class RecycleViewActivity : AppCompatActivity(), (PostModel) -> Unit {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var postList: List<PostModel> = ArrayList()
    private val recycleViewAdapter : RecycleViewAdapter = RecycleViewAdapter(postList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        loadData()
        firestore_list.layoutManager = LinearLayoutManager(this)
        firestore_list.adapter = recycleViewAdapter
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

    fun getPostList(): Task<QuerySnapshot>{
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