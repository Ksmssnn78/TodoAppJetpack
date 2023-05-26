package com.example.todoappjetpack

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoappjetpack.screens.register.SignIn
import com.example.todoappjetpack.screens.splashScreen.SplashScreen
import com.example.todoappjetpack.screens.todo.TodoList

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
//        composable(Screen.SplashScreen.route) {
//            val viewModel: SplashScreenViewModel = hiltViewModel()
//            SplashScreenData(
//                viewModel = viewModel
//            )
//        }
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.SignInScreen.route) {
            SignIn(navController)
        }
        composable(Screen.TodoListScreen.route) {
            TodoList(navController)
        }
    }
}
