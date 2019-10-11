package com.example.retrofitrxjavawithkotlin.network

import com.example.retrofitrxjavawithkotlin.model.Todo
import io.reactivex.Single

class TodoRepository(baseUrl:String,isDebugEnabled:Boolean) :Repository(baseUrl,isDebugEnabled) {
    private val datasource = TodoDataSource(retrofit)

    inner class Result(val todoList:List<Todo>? = null,val errorResponse:String? = null){
        fun hasTodos():Boolean{
            return todoList!=null && !todoList.isEmpty()
        }

        fun hasError():Boolean{
            return errorResponse!=null
        }
    }

    fun getTodoResponse(): Single<Result> {
        return datasource.getTodoDatas()
            .map{Result(todoList = it)}
            .onErrorReturn { Result(errorResponse = it.message) }
    }
}