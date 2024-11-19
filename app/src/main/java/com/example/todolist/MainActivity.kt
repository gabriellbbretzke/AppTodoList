package com.example.todolist

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verifica se o usuário está autenticado
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        if (!isLoggedIn) {
            // Redireciona para LoginActivity se não estiver autenticado
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

        todoAdapter = TodoAdapter(mutableListOf())
        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)
        rvTodoItems.itemAnimator = DefaultItemAnimator()

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvTodoItems)

        btnAddTodo.setOnClickListener{
            val todoTitle = etTodoTile.text.toString()
            if(todoTitle.isNotEmpty()) {
                val itemExists = todoAdapter.todos.any { it.title.equals(todoTitle, ignoreCase = true) }

                if (itemExists) {
                    Toast.makeText(this, "Item já existe na lista", Toast.LENGTH_SHORT).show()
                } else {
                    val todo = Todo(todoTitle)
                    todoAdapter.addTodo(todo)
                    etTodoTile.text.clear()
                }
            }
        }

        btnDeleteDoneTodos.setOnClickListener{
            todoAdapter.deleteDoneTodos(rvTodoItems)
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