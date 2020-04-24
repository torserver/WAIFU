package com.example.waifu.dto

import com.example.waifu.PriorityLevel
import com.example.waifu.ui.TaskViewModel

data class Task (var taskName: String = "", var taskDescription: String = "", var taskPriorityLevel: Int = PriorityLevel.HIGH.priorityLevelNumber, var taskId: String = "")
{
    fun convertToTaskViewModelObject() : TaskViewModel
    {
        return TaskViewModel(this)
    }

    fun getTaskPriorityLevelMessage(): String
    {
        var taskPriorityLevelMessage = ""
        if(taskPriorityLevel == PriorityLevel.HIGH.priorityLevelNumber)
        {
            taskPriorityLevelMessage = "High"
        }
        else if(taskPriorityLevel == PriorityLevel.MEDIUM.priorityLevelNumber)
        {
            taskPriorityLevelMessage = "Medium"
        }
        else if(taskPriorityLevel == PriorityLevel.LOW.priorityLevelNumber)
        {
            taskPriorityLevelMessage = "Low"
        }
        else
        {
            taskPriorityLevelMessage = "Priority Level Not Set"
        }
        return taskPriorityLevelMessage
    }
}