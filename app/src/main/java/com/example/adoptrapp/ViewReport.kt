package com.example.adoptrapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class ViewReport : AppCompatActivity(), (ViewReportModel) -> Unit {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var postList: List<ViewReportModel> = ArrayList()
    private val viewUserRecycleViewAdapter : ViewReportRecycleViewAdapter = ViewReportRecycleViewAdapter(postList,this)

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
                postList = it.result!!.toObjects(ViewReportModel::class.java)
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
            .collection("supportTickets")
            .orderBy("priority", Query.Direction.ASCENDING)
            .get()
    }

    override fun invoke(p1: ViewReportModel) {
        val bundle = bundleOf(
            "id" to p1.id,
            "topic" to p1.topic,
            "desc" to p1.desc,
            "reason" to p1.reason
        )
        val i = Intent(this, ViewReportDisplayTemplate::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }


}