package com.example.coolgamesincompose.signup

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
class SignupViewmodel @Inject constructor() : ViewModel() {
    private val signUpState = MutableStateFlow<SignUpState>(SignUpState.Normal)
    val signUpStateFlow = signUpState.asStateFlow()
    fun signUp(uname: String, pwd: String) {
        val auth = Firebase.auth
        signUpState.value = SignUpState.Loading
        auth.createUserWithEmailAndPassword(uname, pwd)
            .addOnCompleteListener() { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        signUpState.value = SignUpState.Success
                    } else {
                        signUpState.value = SignUpState.Error
                    }
                } else {
                    signUpState.value = SignUpState.Error
                }
            }
    }
}

sealed class SignUpState {
    object Normal : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    object Error : SignUpState()
}