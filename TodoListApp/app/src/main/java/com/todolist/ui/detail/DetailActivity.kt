package com.todolist.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.todolist.data.model.TodoItem
import com.todolist.data.repository.TodoRepository
import com.todolist.ui.viewmodel.TodoViewModel
import com.todolist.ui.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val viewModel: TodoViewModel by viewModels {
        ViewModelFactory(TodoRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setContentView(R.layout.item_todo_details)

        val taskId = intent.getStringExtra("TASK_ID")
        if (!taskId.isNullOrEmpty()) {
            viewModel.fetchTodoById(taskId)
        }

        var taskTitleDetail = findViewById<TextView>(R.id.tvTodoTitleDetail)
        var editTextTextMultiLine = findViewById<EditText>(R.id.editTextTextMultiLine)
//        taskTitleDetail.text = taskTitle

        viewModel.todoItem.observe(this) {todoItem ->
            todoItem?.let {
                taskTitleDetail.text = it.title
                editTextTextMultiLine.setText(it.description)
            }
        }

        val btnSave = findViewById<Button>(R.id.buttonSave)

        btnSave.setOnClickListener {
            val updatedTodo = TodoItem(
                id = taskId,
                title = taskTitleDetail.text.toString(),
                description = editTextTextMultiLine.text.toString()
            )

            viewModel.updateTodo(updatedTodo) // Add update functionality in ViewModel
            finish()
        }
    }
}
