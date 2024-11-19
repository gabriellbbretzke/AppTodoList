package com.example.todolist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTodoTitle: TextView = itemView.findViewById(R.id.tvTodoTitle)
        val cbDone: CheckBox = itemView.findViewById(R.id.cbDone)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo,
            parent,
            false
        )
        return TodoViewHolder(itemView)
    }

    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos(recyclerView: RecyclerView) {
        for (i in todos.size - 1 downTo 0) {
            if (todos[i].isChecked) {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(i)
                viewHolder?.let {
                    val anim = ObjectAnimator.ofFloat(
                        it.itemView,
                        "alpha",
                        1f,
                        0f
                    )
                    anim.duration = 300
                    anim.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            todos.removeAt(i)
                            notifyItemRemoved(i)
                        }
                    })
                    anim.start()
                }
            }
        }
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.tvTodoTitle.text = curTodo.title

        val anim = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f)
        anim.duration = 500
        anim.start()

        // Bind data to the views in the ViewHolder
        holder.tvTodoTitle.text = curTodo.title
        holder.cbDone.isChecked = curTodo.isChecked

        toggleStrikeThrough(holder.tvTodoTitle, curTodo.isChecked)

        // Set a listener on the CheckBox to handle the strike-through
        holder.cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(holder.tvTodoTitle, isChecked)
            curTodo.isChecked = isChecked
        }

        // Configurar o clique do botão de edição
        holder.btnEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("TASK_TITLE", curTodo.title)  // Use curTodo.title aqui
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}
