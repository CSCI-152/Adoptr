package com.example.adoptrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_with_text.view.*

class RecycleViewAdapterArticle (var articleListItem: List<ClassArticle>, val clickListener: (ClassArticle) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(classArticle: ClassArticle, clickListener: (ClassArticle) -> Unit){

            itemView.itemWithTextTitle.text = classArticle.title
            itemView.itemWithTextBody.text = classArticle.description

            itemView.setOnClickListener {
                clickListener(classArticle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_with_text, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleListItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageViewHolder).bind(articleListItem[position], clickListener)
    }

}