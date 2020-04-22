package com.example.waifu.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.RadioGroup
import com.example.waifu.PriorityLevel
import com.example.waifu.R
import kotlinx.android.synthetic.main.activity_create_new_task.*
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.android.synthetic.main.activity_edit_task.etTaskDescription
import kotlinx.android.synthetic.main.activity_edit_task.etTaskName
import kotlinx.android.synthetic.main.row_layout.*

class EditTaskActivity : AppCompatActivity() {

    var taskName: String? = null
    var taskDescription: String ?= null
    var taskPriorityLevel: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        var rgPriority = findViewById<RadioGroup>(R.id.rgPriority)
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
            taskPriorityLevel = "Low"
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
    }
}
