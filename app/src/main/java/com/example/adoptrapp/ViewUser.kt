package com.example.adoptrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ViewUser : AppCompatActivity(), (ViewUserModel) -> Unit {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var postList: List<ViewUserModel> = ArrayList()
    private val viewUserRecycleViewAdapter : ViewUserRecycleViewAdapter = ViewUserRecycleViewAdapter(postList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        loadData()

        val firestoreList = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.firestore_list)
        firestoreList.layoutManager = LinearLayoutManager(this)
        firestoreList.adapter = viewUserRecycleViewAdapter
    }

    private fun loadData(){
        getUserList().addOnCompleteListener{
            if(it.isSuccessful){
                postList = it.result!!.toObjects(ViewUserModel::class.java)
                viewUserRecycleViewAdapter.postListItem = postList
                viewUserRecycleViewAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(applicationContext,"Failed to retrieve data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getUserList(): Task<QuerySnapshot> {
        return firebaseFirestore
            .collection("users")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()
                for (document in it.result!!){
                    result.append(document.data.getValue("role")).append("\n")
                }
                displayResult2.setText(result)
            }
    }
}