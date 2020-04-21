package com.example.waifu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProviders
import com.example.waifu.R
import com.example.waifu.PriorityLevel
import com.example.waifu.dto.Task
import kotlinx.android.synthetic.main.activity_create_new_task.*
import kotlinx.android.synthetic.main.activity_create_new_task.view.*
import kotlinx.android.synthetic.main.activity_main.view.*

class CreateNewTaskActivity : AppCompatActivity()
{

    var enteredPriorityLevel = PriorityLevel.HIGH.priorityLevelNumber
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_task)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        btnSave.setOnClickListener {
            saveTask()
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
            enteredPriorityLevel = PriorityLevel.LOW.priorityLevelNumber
        }
        if(radioGroupPriority.rdoMediumPriority.isChecked)
        {
            //priorityLevel = 2
            enteredPriorityLevel = PriorityLevel.MEDIUM.priorityLevelNumber
        }
        if(radioGroupPriority.rdoHighPriority.isChecked)
        {
            //priorityLevel = 3
            enteredPriorityLevel = PriorityLevel.HIGH.priorityLevelNumber
        }
        return enteredPriorityLevel
    }
    private fun saveTask()
    {
        var task = Task(etTaskName.text.toString(), etTaskDescription.text.toString(), determinePriorityLevel(), taskId.toString())
        viewModel.save(task)
        goBackToMain()
    }
}
