package com.todolist.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todolist.data.model.TodoItem
import com.todolist.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val todos = MutableLiveData<List<TodoItem>>()
    val todoItem = MutableLiveData<TodoItem?>()
    val operationStatus = MutableLiveData<Boolean>() // Para rastrear o sucesso ou falha das operações

    fun fetchTodos() {
        viewModelScope.launch {
            val todoList = repository.getTodos()
            todos.value = todoList // Update the LiveData with the fetched todos
        }
    }

    // Fetch a todo by ID
    fun fetchTodoById(id: String) {
        viewModelScope.launch {
            val fetchedItem = repository.getTodoById(id)
            todoItem.value = fetchedItem
        }
    }

    // Create a new todo
    fun createTodo(todo: TodoItem) {
        viewModelScope.launch {
            val createdItem = repository.createTodo(todo)
            if (createdItem != null) {
                todos.value = todos.value.orEmpty() + createdItem
                operationStatus.value = true
            } else {
                operationStatus.value = false
            }
        }
    }

    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            val updatedItem = repository.updateTodo(todo)
            if (updatedItem != null) {
                todos.value = todos.value?.map {
                    if (it.id == updatedItem.id) updatedItem else it
                }
                operationStatus.value = true
            } else {
                operationStatus.value = false
            }
        }
    }

    fun deleteTodoItem(id: String) {
        viewModelScope.launch {
            val isDeleted = repository.deleteTodo(id)
            if (isDeleted) {
                todos.value = todos.value?.filterNot { it.id == id }
                operationStatus.value = true
            } else {
                operationStatus.value = false
            }
        }
    }
}
