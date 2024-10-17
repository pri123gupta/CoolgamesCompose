package com.example.coolgamesincompose.login

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

   private val loginState = MutableStateFlow<LoginState>(LoginState.Normal)
    val loginStateFlow = loginState.asStateFlow()
    fun login(email: String, password: String) {
        val auth = Firebase.auth
        loginState.value = LoginState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        loginState.value = LoginState.Success
                    } else {
                        loginState.value = LoginState.Error
                    }
                } else loginState.value = LoginState.Error
            }
    }
}

sealed class LoginState {
    object Normal : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    object Error : LoginState()
}