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

class RecycleViewActivityArticle : AppCompatActivity(), (ClassArticle) -> Unit {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var articleList: List<ClassArticle> = ArrayList()
    private val recycleViewAdapter : RecycleViewAdapterArticle = RecycleViewAdapterArticle(articleList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        loadData()

        val firestoreList = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.firestore_list)
        firestoreList.layoutManager = LinearLayoutManager(this)
        firestoreList.adapter = recycleViewAdapter
    }

    private fun loadData(){
        getArticleList().addOnCompleteListener{
            if(it.isSuccessful){
                articleList = it.result!!.toObjects(ClassArticle::class.java)
                recycleViewAdapter.articleListItem = articleList
                recycleViewAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(applicationContext,"Failed to retrieve data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getArticleList(): Task<QuerySnapshot>{
        return firebaseFirestore
            .collection("articles")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
    }

    //passing values onclick
    override fun invoke(classArticle: ClassArticle) {

        val title = classArticle.title.trim()
        val postDate = classArticle.postDate.trim()
        val author = classArticle.authorID.trim()
        val body = classArticle.description.trim()
        val bundle = bundleOf(
            "title" to title,
            "postDate" to postDate,
            "author" to author,
            "body" to body
        )

        val i = Intent(this, TemplateArticleDisplayActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}