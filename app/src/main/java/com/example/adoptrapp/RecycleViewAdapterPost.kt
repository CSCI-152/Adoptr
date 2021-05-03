package com.example.adoptrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_with_image.view.*

class RecycleViewAdapterPost (var postListItem: List<PostModel>, val clickListener: (PostModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postModel: PostModel, clickListener: (PostModel) -> Unit){
            itemView.textView.text = postModel.title

            //load image and bind it here 14:50

            Glide.with(itemView.context).load(postModel.url).into(itemView.imageView2)

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
        return postListItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageViewHolder).bind(postListItem[position], clickListener)
    }

}