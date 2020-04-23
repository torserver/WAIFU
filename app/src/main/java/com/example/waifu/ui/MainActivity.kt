package com.example.waifu.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.RemoteViews
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waifu.R
import com.example.waifu.dto.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    private lateinit var viewModel: MainViewModel
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerView.Adapter<*>? = null
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"


    //where tasks that will appear on cards in the recyclerview are stored for now
    //TODO hook this up to firebase
    public var recyclerViewRowItems: ArrayList <Task> = arrayListOf (
        Task("Test", "Test Description", 2),
        Task("Test2", "Test2 Description", 1),
        Task("Test3", "Test3 Description", 3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)

        //it is a class to notify the user of events that happen.
        // This is how you tell the user that something has happened in the background.
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //onClick listener for the button
        btn.setOnClickListener {

            //pendingIntent is an intent for future use i.e after
            // the notification is clicked, this intent will come into action

            val intent = Intent(this, MainActivity::class.java)

            //FLAG_UPDATE_CURRENT specifies that if a previous
            // PendingIntent already exists, then the current one
            // will update it with the latest intent
            // 0 is the request code, using it later with the
            // same method again will get back the same pending
            // intent for future reference
            //intent passed here is to our afterNotification class
            val pendingIntent = PendingIntent.getActivity(this,
                0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

            //RemoteViews are used to use the content of some different layout apart from the current activity layout

            val contentView = RemoteViews(packageName, R.layout.activity_main)

            //checking if android version is greater than oreo(API 26) or not
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(
                    channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this,channelId)
                    .setContentText("Don't give up on your daily goals")
                    .setSubText("display pending tasks")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                        R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            }else{

                builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                        R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())
        }

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
        recyclerView!!.addOnItemTouchListener(RecyclerTouchListener(applicationContext, recyclerView!!, object : ClickListener
        {
            override fun onClick(view: View, position: Int)
            {
                goToEditTask(view, position)
            }

            override fun onLongClick(view: View?, position: Int)
            {
            }

        }))
        updateRecyclerView()

        btnNewTask.setOnClickListener()
        {
            goToCreateNewTask()
        }
    }

    //takes you to the create task screen
    fun goToCreateNewTask()
    {
        val intent = Intent(this, CreateNewTaskActivity::class.java)
        startActivity(intent)
    }

    //takes you to the edit task screen
    fun goToEditTask(view: View, position: Int)
    {
        val task: Task = recyclerViewRowItems.get(position) //gets the task object (card clicked on) from the recycler view) and sends the data to the edit task page if the card is clicked on
        val intent = Intent(this, EditTaskActivity::class.java)
        intent.putExtra("taskName", task.getTaskName())
        intent.putExtra("taskDescription", task.getTaskDescription())
        intent.putExtra("taskPriorityLevel", task.getTaskPriorityLevel())
        startActivity(intent)
    }

    fun updateRecyclerView() //updates the items that appear on cards in the recycler view
    {
        for (i in 0..recyclerViewRowItems.lastIndex)
        {
            val task = Task()
            task.taskName = task.setTaskName(recyclerViewRowItems[i].getTaskName())
            task.taskDescription = task.setTaskDescription(recyclerViewRowItems[i].getTaskDescription())
            task.taskPriorityLevel = task.setTaskPriorityLevel(recyclerViewRowItems[i].getTaskPriorityLevel())
        }
        recyclerViewRowItems.sortBy { it.taskPriorityLevel} //sorts all tasks in the recyclerview by priority level
    }

    interface ClickListener
    {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(context: Context, recyclerView: RecyclerView, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean
                {
                    return true
                }

                override fun onLongPress(e: MotionEvent)
                {
                    val childView = recyclerView.findChildViewUnder(e.x, e.y)
                    if (childView != null && clickListener != null)
                    {
                        clickListener.onLongClick(childView, recyclerView.getChildAdapterPosition(childView))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(recyclerView: RecyclerView, e: MotionEvent): Boolean
        {
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e))
            {
                clickListener.onClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

}
