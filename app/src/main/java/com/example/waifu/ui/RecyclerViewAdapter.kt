package com.example.waifu.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.waifu.PriorityLevel
import com.example.waifu.R
import com.example.waifu.databinding.TaskBinding
import java.util.ArrayList

class RecyclerViewAdapter(private val context:Context, private val recyclerViewRowItems:ArrayList<TaskViewModel>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val taskBinding: TaskBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_layout, parent, false)
        return RecyclerViewViewHolder(taskBinding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int)
    {
        val taskViewModel = recyclerViewRowItems[position]
        holder.bind(taskViewModel) //adds all attributes to the card view item
        //color changing logic
        when (taskViewModel.taskPriorityLevel) {
            PriorityLevel.HIGH.priorityLevelNumber -> {
                holder.cardView.setCardBackgroundColor(Color.RED)
            }
            PriorityLevel.MEDIUM.priorityLevelNumber-> {
                holder.cardView.setCardBackgroundColor(Color.YELLOW)
            }
            else -> {
                holder.cardView.setCardBackgroundColor(Color.GREEN)
            }
        }
    }

    override fun getItemCount(): Int
    {
        return recyclerViewRowItems.size
    }

    inner class RecyclerViewViewHolder(val taskBinding: TaskBinding) :RecyclerView.ViewHolder(taskBinding.root)
    {
        fun bind(taskViewModel: TaskViewModel)
        {
            this.taskBinding.taskmodel = taskViewModel
            taskBinding.executePendingBindings()
        }

        internal var cardView : CardView

        init
        {
            cardView = itemView.findViewById<View>(R.id.cardView) as CardView
        }
    }
}