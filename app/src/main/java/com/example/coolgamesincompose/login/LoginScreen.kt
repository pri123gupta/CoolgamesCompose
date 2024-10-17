package com.example.coolgamesincompose.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun LoginScreen(
    navController: NavController
) {

    val result = remember { mutableStateOf("Normal") }
    val viewModel: LoginViewModel = hiltViewModel()
    val isLoading = remember { mutableStateOf(false) }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val state = viewModel.loginStateFlow.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Screen - ${result.value}")
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Username") })

        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") }
        )
        LaunchedEffect(state.value) {
            when (state.value) {
                is LoginState.Normal -> {
                    result.value = "normal"
                    isLoading.value = false
                }

                is LoginState.Loading -> {
                    result.value = "Loading"
                    isLoading.value = true
                }

                is LoginState.Success -> {
                    result.value = "success"
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }

                is LoginState.Error -> {
                    result.value = "Error"
                    isLoading.value = false
                }
            }
        }

        if (isLoading.value) {
            CircularProgressIndicator()
        } else {
            Button(onClick = {
                viewModel.login(username.value, password.value)
            }, Modifier.fillMaxWidth()) {
                Text("Login")
            }
            TextButton(onClick = {
                navController.navigate("signup")
            }) {
                Text(text = "Don't have an account ? Sign Up")
            }
        }
    }
}

@Preview(backgroundColor = 0xFFDDDDDD, showBackground = true)
@Composable
fun LoginScreenPreview(modifier: Modifier = Modifier) {
    LoginScreen(rememberNavController())
}