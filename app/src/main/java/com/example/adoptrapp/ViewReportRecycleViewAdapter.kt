package com.example.adoptrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_report_item.view.*

class ViewReportRecycleViewAdapter (var postListItem: List<ViewReportModel>, val clickListener: (ViewReportModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            fun bind(postModel: ViewReportModel, clickListener: (ViewReportModel) -> Unit){
                itemView.view_report_item.text = postModel.reason + " - " + postModel.desc
                itemView.setOnClickListener {
                    clickListener(postModel)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_report_item, parent, false)
            return ImageViewHolder(view)
        }

        override fun getItemCount(): Int {
            return postListItem.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ImageViewHolder).bind(postListItem[position], clickListener)
        }

    }
