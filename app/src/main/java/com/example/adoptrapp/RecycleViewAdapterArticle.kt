package com.example.adoptrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_with_image.view.*

class RecycleViewAdapterArticle (var articleListItem: List<ClassArticle>, val clickListener: (ClassArticle) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postModel: ClassArticle, clickListener: (ClassArticle) -> Unit){
            itemView.textView.text = postModel.title
            //load image and bind it here 14:50

            itemView.setOnClickListener {
                clickListener(postModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_with_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleListItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageViewHolder).bind(articleListItem[position], clickListener)
    }

}