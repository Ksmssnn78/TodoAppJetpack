package com.example.todoappjetpack.screens.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodo.Utils.Extensions.SharedPref
import com.example.roomtodo.models.User
import com.example.roomtodo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val userName = "mashraf9881@gmail.com"
    private val password = "123456789"
    private val user = User(userName = userName, password = password)

    @Inject
    lateinit var sharedPref: SharedPref

    private val _isOk: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isOk: LiveData<Boolean>
        get() = _isOk

    fun defaultUser() {
        viewModelScope.launch {
            userRepository.insertOneUser(user)
        }
    }

    fun checkAuth() {
        if (sharedPref.getUser() != null) {
            _isOk.postValue(true)
        } else {
            _isOk.postValue(false)
        }
    }
}
