package com.example.waifu.dto

import com.example.waifu.PriorityLevel

data class Task (var taskName: String = "", var taskDescription: String = "", var taskPriorityLevel: Int = PriorityLevel.HIGH.priorityLevelNumber, var taskId: String = "")