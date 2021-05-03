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

class FragmentArticle: Fragment(), (ClassArticle) -> Unit {

    companion object {
        // provide a new instance of the HomeFragment
        fun newInstance() : FragmentArticle {
            return FragmentArticle()
        }
    }
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var articleList: List<ClassArticle> = ArrayList()
    private val recycleViewAdapter : RecycleViewAdapterArticle = RecycleViewAdapterArticle(articleList,this)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)


        loadDataArticles()
        val firestoreList = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.firestore_list_article)
        firestoreList.layoutManager = LinearLayoutManager(this.context)
        firestoreList.adapter = recycleViewAdapter


        return view
    }
    private fun loadDataArticles(){
        getArticlesList().addOnCompleteListener{
            if(it.isSuccessful){
                articleList = it.result!!.toObjects(ClassArticle::class.java)
                recycleViewAdapter.articleListItem = articleList
                recycleViewAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(this.context,"Failed to retrieve data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getArticlesList(): Task<QuerySnapshot> {
        return firebaseFirestore
            .collection("articles")
            //.orderBy("date", Query.Direction.DESCENDING)
            .get()
    }
    override fun invoke(classArticle: ClassArticle) {
        // Need to put make this work for articles.
        val title = classArticle.title.toString().trim()
        val postDate = classArticle.postDate.toString().trim()
        val author = classArticle.authorID.toString().trim()
        val body = classArticle.description.toString().trim()
        val bundle = bundleOf(
            "title" to title,
            "postDate" to postDate,
            "author" to author,
            "body" to body
        )
        val i = Intent(this.context, TemplateArticleDisplayActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}