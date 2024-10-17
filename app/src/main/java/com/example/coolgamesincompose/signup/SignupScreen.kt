package com.example.coolgamesincompose.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignupScreen(navController: NavController) {

    val result = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val signupViewmodel: SignupViewmodel = hiltViewModel()
    val loadingState = remember { mutableStateOf(false) }
    val state = signupViewmodel.signUpStateFlow.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SignUp Screen ${result.value}")
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Username") })

        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") })
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            label = { Text(text = "Confirm Password") }
        )
        LaunchedEffect(state.value) {
            when (state.value) {
                is SignUpState.Normal -> {
                    result.value = "normal"
                    loadingState.value = false
                }

                is SignUpState.Loading -> {
                    result.value = "loading"
                    loadingState.value = true
                }

                is SignUpState.Success -> {
                    result.value = "success"
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }

                is SignUpState.Error -> {
                    result.value = "error"
                    loadingState.value = false
                }
            }
        }
        if (loadingState.value) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    signupViewmodel.signUp(username.value, password.value)
                }, Modifier.fillMaxWidth(),
                enabled = username.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty() && password.value == confirmPassword.value
            ) {
                Text("Signup")
            }
        }
    }
}

@Preview(backgroundColor = 0xFFDDDDDD, showBackground = true)
@Composable
fun SignupScreenPreview(modifier: Modifier = Modifier) {
    SignupScreen(rememberNavController())
}