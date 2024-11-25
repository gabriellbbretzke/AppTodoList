package com.todolist.data.network

import com.todolist.data.api.TodoApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:5067/api/"

    // OkHttp client with custom SSL configuration
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                //.header("Authorization", "Bearer YOUR_TOKEN_HERE") // Add auth headers if needed
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: TodoApiService = retrofit.create(TodoApiService::class.java)
}
