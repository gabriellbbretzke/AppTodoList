package com.example.todolist

data class TodoItem(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: String
)
