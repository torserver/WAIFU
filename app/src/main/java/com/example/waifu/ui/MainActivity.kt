package com.example.waifu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waifu.R
import com.example.waifu.dto.Task

class MainActivity : AppCompatActivity()
{
    private lateinit var viewModel: MainViewModel
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerView.Adapter<*>? = null
    var recyclerViewRowItems: ArrayList <Task> = arrayListOf (
        Task("Test", "Test Description", 1),
        Task("Test2", "Test2 Description", 2),
        Task("Test3", "Test3 Description", 3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.tasks.observe(this, Observer {
            //TODO //spinner population retrofit required (only designed to work with spinner) /update logic
            //tasks -> tskTasks.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, tasks))
        })

        recyclerView = findViewById(R.id.recyclerView)
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView!!.layoutManager = layoutManager
        recyclerViewAdapter = RecyclerViewAdapter(recyclerViewRowItems)
        recyclerView!!.adapter = recyclerViewAdapter
        populateRecyclerView()


    }

    fun goToCreateNewTask(view: View)
    {
        val intent = Intent(this, CreateNewTaskActivity::class.java)
        startActivity(intent)
    }

    fun populateRecyclerView()
    {
        for (i in 0..recyclerViewRowItems.lastIndex)
        {
            val task = Task()
            task.taskName = task.setTaskName(recyclerViewRowItems[i].getTaskName())
            task.taskDescription = task.setTaskDescription(recyclerViewRowItems[i].getTaskDescription())
            task.taskPriorityLevel = task.setTaskPriorityLevel(recyclerViewRowItems[i].getTaskPriorityLevel())
        }
    }

}
