package com.example.waifu.ui

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waifu.PriorityLevel
import com.example.waifu.dto.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class TaskViewModel : ViewModel
{
    //logic
    var taskName = ""
    var taskDescription = ""
    var taskPriorityLevel = PriorityLevel.HIGH.priorityLevelNumber
    var taskPriorityLevelString = ""
    var taskId = ""

    private var firestore: FirebaseFirestore

    //firebase stuff

    init
    {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToTasks()
    }

    constructor() : super()
    constructor(task : Task) : super()
    {
        this.taskName = task.taskName
        this.taskDescription = task.taskDescription
        this.taskPriorityLevel = task.taskPriorityLevel
        this.taskId = task.taskId
        this.taskPriorityLevelString = task.getTaskPriorityLevelMessage()
    }

    var _recyclerViewLiveData = MutableLiveData<ArrayList<TaskViewModel>>()

    var recyclerViewData = ArrayList<TaskViewModel>()

    /**
     * This will hear any updates from firestore
     */
    private fun listenToTasks()
    {
        firestore.collection("tasks").addSnapshotListener()
        {
                snapshot, e ->
            //if there is an exception, skip
            if(e !=null)
            {
                Log.w(ContentValues.TAG, "Listen failed", e)
                return@addSnapshotListener
            }
            //if there is no exception
            else
            {
                if(snapshot != null)
                {
                    //populate snapshot
                    val documents = snapshot.documents
                    documents.forEach()
                    {
                        val task = it.toObject(Task::class.java) //convert it to Task object
                        if (task != null)
                        {
                            var taskViewModel = task.convertToTaskViewModelObject()
                            recyclerViewData.add(taskViewModel!!) //if task is not null, add it to the task list.
                        }
                    }
                    _recyclerViewLiveData.value = recyclerViewData
                }
            }
        }
    }

    //fun save(task: Task) : TaskViewModel
    fun save(task: Task)
    {
        val document = firestore.collection("tasks").document()
        task.taskId = document.id
        val set = document.set(task)
        set.addOnSuccessListener {
            Log.d("Firebase", "Document saved")
        }
        set.addOnFailureListener() {
            Log.d("Firebase", "Save failed")
        }
    }


    internal var retrieveRecycleViewData:MutableLiveData<ArrayList<TaskViewModel>>
        get() { return _recyclerViewLiveData }
        set(value) {_recyclerViewLiveData = value}

}