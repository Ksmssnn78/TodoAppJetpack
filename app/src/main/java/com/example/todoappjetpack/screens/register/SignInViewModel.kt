package com.example.todoappjetpack.screens.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodo.Utils.Extensions.SharedPref
import com.example.roomtodo.networks.ApiExceptions
import com.example.roomtodo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _isValid: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isValid: LiveData<Boolean>
        get() = _isValid

    @Inject
    lateinit var sharedPref: SharedPref

    fun authSignIn(uname: String?, pass: String?) {
        var flag: Boolean
        viewModelScope.launch {
            try {
                flag = if (uname != null && pass != null) {
                    userRepository.getOneUser(uname, pass)
                } else {
                    false
                }
                if (flag && uname != null) {
                    sharedPref.saveUser(uname)
                }
                _isValid.postValue(flag)
            } catch (e: ApiExceptions) {
                _isValid.postValue(false)
                Timber.e("Login failed")
            }
        }
    }
}
