package com.todolist.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.todolist.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setContentView(R.layout.item_todo_details)

        val taskTitle = intent.getStringExtra("TASK_TITLE")

        val taskTitleDetail = findViewById<TextView>(R.id.tvTodoTitleDetail)
        taskTitleDetail.text = taskTitle

        val btnSave = findViewById<Button>(R.id.buttonSave)

        btnSave.setOnClickListener {
            finish()
        }

    }
}
