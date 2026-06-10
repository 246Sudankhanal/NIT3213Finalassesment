package com.example.nit3213

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nit3213.ui.dashboard.DashboardScreen
import com.example.nit3213.ui.dashboard.DashboardViewModel
import com.example.nit3213.ui.login.LoginScreen
import com.example.nit3213.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { keypass ->
                    navController.navigate("dashboard/$keypass")
                }
            )
        }
        composable("dashboard/{keypass}") { backStackEntry ->
            val keypass = backStackEntry.arguments?.getString("keypass") ?: ""
            val viewModel: DashboardViewModel = hiltViewModel()
            DashboardScreen(
                keypass = keypass,
                viewModel = viewModel,
                onEntityClick = { entity ->
                    // Navigate to details screen (to be implemented)
                }
            )
        }
    }
}
