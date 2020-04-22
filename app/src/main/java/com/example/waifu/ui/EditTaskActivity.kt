package com.example.waifu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import com.example.waifu.R
import kotlinx.android.synthetic.main.activity_edit_task.*

class EditTaskActivity : AppCompatActivity() {

    var taskName: String? = null
    var taskDescription: String ?= null
    var taskPriorityLevel: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        var rgPriority = findViewById<RadioGroup>(R.id.rgPriority)
        var btnBack = findViewById<Button>(R.id.btnBack)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            taskName = bundle!!.getString("taskName")
            taskDescription = bundle!!.getString("taskDescription")
            taskPriorityLevel = bundle!!.getString("taskPriorityLevel")
        }
        else
        {
            taskName = "Task Name"
            taskDescription = "Task Description"
            taskPriorityLevel = "High"
        }
        if (taskPriorityLevel.equals("High"))
        {
            rgPriority.check(R.id.rdoHighPriority)
        }
        else if (taskPriorityLevel.equals("Medium"))
        {
            rgPriority.check(R.id.rdoMediumPriority)
        }
        else
        {
            rgPriority.check(R.id.rdoLowPriority)
        }

        etTaskName.setText(taskName)
        etTaskDescription.setText(taskDescription)

        btnSave.setOnClickListener()
        {
            goBackToMain()
        }
    }
    //goes back to the task list page
    fun goBackToMain()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
