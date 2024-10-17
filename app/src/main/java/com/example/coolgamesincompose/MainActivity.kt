package com.example.coolgamesincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coolgamesincompose.home.HomeScreen
import com.example.coolgamesincompose.login.LoginScreen
import com.example.coolgamesincompose.signup.SignupScreen
import com.example.coolgamesincompose.ui.theme.CoolGamesInComposeTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoolGamesInComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        NavHostScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun NavHostScreen() {
    var start = "login"
    FirebaseAuth.getInstance().currentUser?.let {
        start = "login"
    }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = start) {
        composable(route = "login") {
            LoginScreen(navController)
        }
        composable("signup") {
            SignupScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
    }
}