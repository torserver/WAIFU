package com.example.waifu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val etTaskName = findViewById<EditText>(R.id.etTaskName)
        val etTaskDescription = findViewById<EditText>(R.id.etTaskDescription)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        taskName = etTaskName.text.toString()
        taskDescription = etTaskDescription.text.toString()

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

    private fun validInputs(): Boolean
    {
        var errorMessage = ""

        if((taskName != null && taskDescription != null) || (!(taskName.equals("")) && !(taskDescription.equals(""))))
        {
            return true
        }
        else
        {
            if((taskName == null && taskDescription == null) || (taskName.equals("") && taskDescription.equals("")))
            {
                errorMessage = "Please put the name and description of your task."
            }
            else if(taskName == null || taskName.equals(""))
            {
                errorMessage = "Please put the name of your task."
            }
            else if (taskDescription == null || taskDescription.equals(""))
            {
                errorMessage = "Please put the description of your task."
            }

            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT)
            return false
        }
    }
}
