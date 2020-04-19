package com.example.waifu.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waifu.R
import com.example.waifu.dto.Task
import java.util.ArrayList

class RecyclerViewAdapter(private val recyclerViewRowItems: ArrayList<Task>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>()
{

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
            return RecyclerViewViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
            holder.lblTaskName.text = recyclerViewRowItems[position].getTaskName()
            holder.lblTaskDescription.text = recyclerViewRowItems[position].getTaskDescription()
            holder.lblPriorityLevel.text = recyclerViewRowItems[position].getTaskPriorityLevelMessage()
        }

        override fun getItemCount(): Int {
            return recyclerViewRowItems.size
        }

        inner class RecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        {
            internal var lblTaskName: TextView
            internal var lblTaskDescription: TextView
            internal var lblPriorityLevel: TextView

            init
            {
                lblTaskName = itemView.findViewById<View>(R.id.lblTaskName) as TextView
                lblTaskDescription = itemView.findViewById<View>(R.id.lblTaskDescription) as TextView
                lblPriorityLevel = itemView.findViewById<View>(R.id.lblTaskPriorityLevel) as TextView
            }
        }
}