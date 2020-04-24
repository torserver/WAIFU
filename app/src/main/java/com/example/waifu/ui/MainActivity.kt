package com.example.waifu.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waifu.R
import com.example.waifu.dto.Task
import kotlinx.android.synthetic.main.activity_create_new_task.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_layout.*

class MainActivity : AppCompatActivity()
{
    private lateinit var taskViewModel: TaskViewModel
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerView.Adapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        taskViewModel.retrieveRecycleViewData.observe( this, Observer
        {
            taskViewModels->
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity,taskViewModels!!)
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView!!.layoutManager = layoutManager
            recyclerView!!.adapter = recyclerViewAdapter

        })


        recyclerView!!.addOnItemTouchListener(RecyclerTouchListener(applicationContext, recyclerView!!, object : ClickListener
        {
            override fun onClick(view: View, position: Int)
            {
                goToEditTask(view, position)
            }

            override fun onLongClick(view: View?, position: Int)
            {
            }

        }))
        //updateRecyclerView()

        btnNewTask.setOnClickListener()
        {
            goToCreateNewTask()
        }
    }

    //takes you to the create task screen
    fun goToCreateNewTask()
    {
        val intent = Intent(this, CreateNewTaskActivity::class.java)
        startActivity(intent)
    }

    //takes you to the edit task screen
    fun goToEditTask(view: View, position: Int)
    {
        //val task: Task = recyclerViewRowItems.get(position) //gets the task object (card clicked on) from the recycler view) and sends the data to the edit task page if the card is clicked on
        val intent = Intent(this, EditTaskActivity::class.java)
    /*    intent.putExtra("taskName", task.getTaskName())
        intent.putExtra("taskDescription", task.getTaskDescription())
        intent.putExtra("taskPriorityLevel", task.getTaskPriorityLevel())*/
        startActivity(intent)
    }

    /*fun updateRecyclerView() //updates the items that appear on cards in the recycler view
    {
        for (i in 0..recyclerViewRowItems.lastIndex)
        {
            val task = Task()
            task.taskName = task.setTaskName(recyclerViewRowItems[i].getTaskName())
            task.taskDescription = task.setTaskDescription(recyclerViewRowItems[i].getTaskDescription())
            task.taskPriorityLevel = task.setTaskPriorityLevel(recyclerViewRowItems[i].getTaskPriorityLevel())
        }
        recyclerViewRowItems.sortBy { it.taskPriorityLevel} //sorts all tasks in the recyclerview by priority level
    }*/

    interface ClickListener
    {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(context: Context, recyclerView: RecyclerView, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean
                {
                    return true
                }

                override fun onLongPress(e: MotionEvent)
                {
                    val childView = recyclerView.findChildViewUnder(e.x, e.y)
                    if (childView != null && clickListener != null)
                    {
                        clickListener.onLongClick(childView, recyclerView.getChildAdapterPosition(childView))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(recyclerView: RecyclerView, e: MotionEvent): Boolean
        {
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e))
            {
                clickListener.onClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

}
