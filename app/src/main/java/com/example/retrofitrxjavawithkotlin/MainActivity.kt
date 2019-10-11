package com.example.retrofitrxjavawithkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.retrofitrxjavawithkotlin.network.TodoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()
    var latestCall:Disposable?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getTodos()

    }

    private fun getTodos() {
        val todoRepository = TodoRepository("https://jsonplaceholder.typicode.com/",BuildConfig.DEBUG)
        latestCall?.dispose()
        latestCall = todoRepository.getTodoResponse()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { compositeDisposable.add(it) }
            .subscribe {
                result ->
                    when{
                        result.hasTodos() -> result.todoList ?.let {
                            for(item in it)
                                Log.d("==>",item.title)
                        } ?: run{ Toast.makeText(this,"Null response",Toast.LENGTH_SHORT).show()}
                        result.hasError() -> Toast.makeText(this,result.errorResponse,Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this,"null response",Toast.LENGTH_SHORT).show()

                    }
            }

    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}
