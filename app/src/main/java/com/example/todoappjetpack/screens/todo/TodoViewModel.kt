package com.example.todoappjetpack.screens.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodo.Utils.Extensions.SharedPref
import com.example.roomtodo.models.TodoModel
import com.example.roomtodo.networks.ApiExceptions
import com.example.roomtodo.repositories.TodoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoListRepository: TodoListRepository
) : ViewModel(){

    private val _taskList: MutableLiveData<List<TodoModel>> by lazy {
        MutableLiveData<List<TodoModel>>()
    }
    val taskList: LiveData<List<TodoModel>?>
        get() = _taskList

    @Inject
    lateinit var sharedPref: SharedPref

    fun getTaskList() = viewModelScope.launch {
        val user: String? = sharedPref.getUser()
        if (user != null) {
            try {
                val todoList = todoListRepository.getAllTask(user)
                _taskList.postValue(todoList)
            } catch (e: ApiExceptions) {
                _taskList.postValue(listOf())
                Timber.e("unable to get data from database")
            }
        }
    }

    fun insertTask(task: String?) = viewModelScope.launch {
        val user:String? = sharedPref.getUser()
        if (task != null && user != null) {
            try {
                val tasks = TodoModel(user = user, task = task, check = false)
                todoListRepository.insertTodo(tasks)
                getTaskList()
            } catch (e: ApiExceptions) {
                Timber.e("unable to insert into database")
            }
        }
    }
    fun updateTask(newTask: TodoModel) = viewModelScope.launch {
        try {
            todoListRepository.updateTodo(newTask)
            getTaskList()
        } catch (e: ApiExceptions) {
            Timber.e("unable to update data")
        }
    }
}