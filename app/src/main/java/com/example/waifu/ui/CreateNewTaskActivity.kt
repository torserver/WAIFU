package com.example.waifu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.example.waifu.R
import com.example.waifu.PriorityLevel
import com.example.waifu.dto.Task
import kotlinx.android.synthetic.main.activity_create_new_task.btnSave
import kotlinx.android.synthetic.main.activity_create_new_task.etTaskDescription
import kotlinx.android.synthetic.main.activity_create_new_task.etTaskName
import kotlinx.android.synthetic.main.activity_create_new_task.view.*
import kotlinx.android.synthetic.main.activity_edit_task.*

class CreateNewTaskActivity : AppCompatActivity()
{

    private lateinit var viewModel: MainViewModel
    private lateinit var taskName: String
    private lateinit var taskDescription: String
    private var taskPriorityLevel = PriorityLevel.HIGH.priorityLevelNumber

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_task)

        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnSave = findViewById<Button>(R.id.btnSave)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        btnBack.setOnClickListener()
        {
            goBackToMain()
        }

        btnSave.setOnClickListener()
        {
            if(validInputs())
            {
                saveTask()
            }
        }
    }

    //goes back to the task list page
    fun goBackToMain()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun determinePriorityLevel(): Int
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
        var task = Task(etTaskName.text.toString(), etTaskDescription.text.toString(), determinePriorityLevel(), taskId.toString())
        viewModel.save(task)
        goBackToMain()
    }

    //validate user inputs
    private fun validInputs(): Boolean
    {
        var errorMessage = ""
        taskName = etTaskName.text.toString()
        taskDescription = etTaskDescription.text.toString()

        if((!(taskName.isNullOrEmpty()) && !(taskDescription.isNullOrEmpty())))
        {
            return true
        }
        else
        {
            if(taskName.isNullOrEmpty() && taskDescription.isNullOrEmpty())
            {
                errorMessage = "Please put the name and description of your task."
            }
            else if(taskName.isNullOrEmpty())
            {
                errorMessage = "Please put the name of your task."
            }
            else if (taskDescription.isNullOrEmpty())
            {
                errorMessage = "Please put the description of your task."
            }
            var toast: Toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 20)
            toast.show()
            return false
        }
    }
}
