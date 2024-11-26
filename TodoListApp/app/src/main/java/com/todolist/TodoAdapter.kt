package com.todolist

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
import com.example.todolist.R
import com.todolist.data.model.TodoItem
import com.todolist.ui.detail.DetailActivity

class TodoAdapter(
    val todos: MutableList<TodoItem>,
    private val onTodoUpdated: (TodoItem) -> Unit
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
        // Remove listener antigo antes de atualizar o estado do checkbox
        holder.cbDone.setOnCheckedChangeListener(null)
        holder.cbDone.isChecked = curTodo.isCompleted

        holder.tvTodoTitle.text = curTodo.title
        holder.cbDone.isChecked = curTodo.isCompleted

        toggleStrikeThrough(holder.tvTodoTitle, curTodo.isCompleted)

        holder.cbDone.setOnCheckedChangeListener { _, isCompleted ->
            toggleStrikeThrough(holder.tvTodoTitle, isCompleted)
            curTodo.isCompleted = isCompleted
            onTodoUpdated(curTodo)
        }

        val anim = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f)
        anim.duration = 500
        anim.start()

        holder.btnEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("TASK_ID", curTodo.id)
            context.startActivity(intent)
        }
    }

    fun updateTodos(newTodos: List<TodoItem>) {
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = todos.size
}
