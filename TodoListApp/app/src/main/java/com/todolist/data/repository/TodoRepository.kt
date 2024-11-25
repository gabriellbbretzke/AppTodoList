package com.todolist.data.repository

import com.todolist.data.model.TodoItem
import com.todolist.data.network.RetrofitInstance

class TodoRepository {
    suspend fun getTodos(): List<TodoItem> {
        return try {
            val response = RetrofitInstance.apiService.getTodos()
            if (response.isSuccessful) {
                response.body() ?: emptyList() // Return the list of todos or an empty list if null
            } else {
                emptyList() // Return an empty list if the response is not successful
            }
        } catch (e: Exception) {
            emptyList() // Return an empty list in case of an error
        }
    }

    // Get a specific todo by ID
    suspend fun getTodoById(id: String): TodoItem? {
        return try {
            val response = RetrofitInstance.apiService.getTodoById(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    // Create a new todo
    suspend fun createTodo(todo: TodoItem): TodoItem? {
        return try {
            val response = RetrofitInstance.apiService.createTodo(todo)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    // Update an existing todo
    suspend fun updateTodo(todo: TodoItem): TodoItem? {
        return try {
            val response = RetrofitInstance.apiService.updateTodo(todo)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteTodo(id: String): Boolean {
        return try {
            val response = RetrofitInstance.apiService.deleteTodo(id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
