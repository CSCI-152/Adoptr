package com.example.adoptrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_user_item.view.*

class ViewUserRecycleViewAdapter(var postListItem: List<ViewUserModel>, val clickListener: (ViewUserModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postModel: ViewUserModel, clickListener: (ViewUserModel) -> Unit){
            itemView.view_user_item.text = postModel.fullName
            itemView.setOnClickListener {
                clickListener(postModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postListItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageViewHolder).bind(postListItem[position], clickListener)
    }

}