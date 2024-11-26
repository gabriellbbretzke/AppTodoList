package com.todolist.ui.main

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.todolist.ui.login.LoginActivity
import com.todolist.TodoAdapter
import com.todolist.data.model.TodoItem
import com.todolist.data.repository.TodoRepository
import com.todolist.ui.viewmodel.TodoViewModel
import com.todolist.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private val viewModel: TodoViewModel by viewModels {
        ViewModelFactory(TodoRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        if (!isLoggedIn) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        val rvTodoItems = findViewById<RecyclerView>(R.id.rvTodoItems)
        val btnAddTodo = findViewById<ImageButton>(R.id.btnAddTodo)
        val btnDeleteDoneTodos = findViewById<ImageButton>(R.id.btnDeleteDoneTodos)
        val etTodoTile = findViewById<EditText>(R.id.etTodoTitle)

        todoAdapter = TodoAdapter(mutableListOf()){ updatedTodo ->
            viewModel.updateTodo(updatedTodo)
        }
        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)
        rvTodoItems.itemAnimator = DefaultItemAnimator()

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvTodoItems)

        // Observe todos LiveData
        viewModel.todos.observe(this) { todoList ->
            todoAdapter.updateTodos(todoList) // Update the adapter when data changes
        }

        viewModel.fetchTodos()

        btnAddTodo.setOnClickListener{
            val todoTitle = etTodoTile.text.toString()
            if(todoTitle.isNotEmpty()) {
                viewModel.createTodo(TodoItem(todoTitle))
            }
        }

        btnDeleteDoneTodos.setOnClickListener{
            val doneTodos = viewModel.todos.value?.filter { it.isCompleted } ?: emptyList()

            if (doneTodos.isNotEmpty()){
                doneTodos.forEach { todoItem ->
                    viewModel.deleteTodoItem(todoItem.id.toString())
                }
            }

            viewModel.todos.observe(this) { updatedTodos ->
                todoAdapter.updateTodos(updatedTodos)
            }
        }
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            todoAdapter.todos.add(toPosition, todoAdapter.todos.removeAt(fromPosition))
            todoAdapter.notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            todoAdapter.todos.removeAt(position)
            todoAdapter.notifyItemRemoved(position)
        }

        override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
        ) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                val paint = Paint()
                paint.color = Color.RED

                val itemView = viewHolder.itemView
                if (dX > 0) {
                    c.drawRect(
                        itemView.left.toFloat(),
                        itemView.top.toFloat(),
                        itemView.left + dX,
                        itemView.bottom.toFloat(),
                        paint
                    )
                } else {
                    c.drawRect(
                        itemView.right + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat(),
                        paint
                    )
                }
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}