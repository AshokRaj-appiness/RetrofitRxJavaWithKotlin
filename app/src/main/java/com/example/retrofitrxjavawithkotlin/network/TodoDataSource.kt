package com.example.retrofitrxjavawithkotlin.network

import com.example.retrofitrxjavawithkotlin.model.Todo
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET

class TodoDataSource(retrofit: Retrofit) {
    val todoApi =retrofit.create(TodoApi::class.java)
    fun getTodoDatas() = todoApi.getTodos()

    interface TodoApi{
        @GET("todos/")
        fun getTodos(): Single<List<Todo>>
    }
}