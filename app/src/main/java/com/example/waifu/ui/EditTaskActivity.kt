package com.example.waifu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import com.example.waifu.PriorityLevel
import com.example.waifu.R
import com.example.waifu.dto.Task
import kotlinx.android.synthetic.main.activity_create_new_task.*
import kotlinx.android.synthetic.main.activity_create_new_task.view.*
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.android.synthetic.main.activity_edit_task.etTaskDescription
import kotlinx.android.synthetic.main.activity_edit_task.etTaskName

class EditTaskActivity : AppCompatActivity() {

//    private lateinit var viewModel: MainViewModel

    //task attributes
    var taskName: String? = null
    var taskDescription: String ?= null
    var taskPriorityLevel: Int ?= null

    //populate edittaskactivity with data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        var rgPriority = findViewById<RadioGroup>(R.id.rgPriority)
        var btnBack = findViewById<Button>(R.id.btnBack)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            taskName = bundle!!.getString("taskName")
            taskDescription = bundle!!.getString("taskDescription")
            taskPriorityLevel = bundle!!.getInt("taskPriorityLevel")
        }
        else
        {
            taskName = "Task Name"
            taskDescription = "Task Description"
            taskPriorityLevel = PriorityLevel.HIGH.priorityLevelNumber
        }
        if (taskPriorityLevel == PriorityLevel.HIGH.priorityLevelNumber)
        {
            rgPriority.check(R.id.rdoHighPriority)
        }
        else if (taskPriorityLevel == PriorityLevel.MEDIUM.priorityLevelNumber)
        {
            rgPriority.check(R.id.rdoMediumPriority)
        }
        else
        {
            rgPriority.check(R.id.rdoLowPriority)
        }

        etTaskName.setText(taskName)
        etTaskDescription.setText(taskDescription)

        btnBack.setOnClickListener()
        {
            goBackToMain()
        }

        btnSave.setOnClickListener()
        {
            saveTask()
        }
    }

    fun determinePriorityLevel(): Int?
    {
        val radioGroupPriority = findViewById<RadioGroup>(R.id.rgPriority)
        if(radioGroupPriority.rdoLowPriority.isChecked)
        {
            //priorityLevel = 1
            taskPriorityLevel = PriorityLevel.LOW.priorityLevelNumber
        }
        if(radioGroupPriority.rdoMediumPriority.isChecked)
        {
            //priorityLevel = 2
            taskPriorityLevel = PriorityLevel.MEDIUM.priorityLevelNumber
        }
        if(radioGroupPriority.rdoHighPriority.isChecked)
        {
            //priorityLevel = 3
            taskPriorityLevel = PriorityLevel.HIGH.priorityLevelNumber
        }
        return taskPriorityLevel
    }

    private fun saveTask()
    {
//        var task = determinePriorityLevel()?.let {
//            Task(etTaskName.text.toString(), etTaskDescription.text.toString(),
//                it, taskId.toString())
//        }
//        if (task != null) {
//            viewModel.save(task)
//        }
        goBackToMain()
    }
    //goes back to the task list page
    private fun goBackToMain()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
