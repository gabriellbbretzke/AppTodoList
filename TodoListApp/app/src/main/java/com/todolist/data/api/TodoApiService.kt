package com.todolist.data.api

import com.todolist.data.model.DeleteResponse
import com.todolist.data.model.TodoItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApiService {
    @GET("TodoItem")
    suspend fun getTodos(): Response<List<TodoItem>>

    @POST("TodoItem")
    suspend fun createTodo(@Body todo: TodoItem): Response<TodoItem>

    @PUT("TodoItem")
    suspend fun updateTodo(@Body todo: TodoItem): Response<TodoItem>

    @DELETE("TodoItem")
    suspend fun deleteTodo(@Query("id") id: String): Response<DeleteResponse>

    @GET("TodoItem/{id}")
    suspend fun getTodoById(@Path("id") id: String): Response<TodoItem>
}