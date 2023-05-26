package com.example.todoappjetpack

sealed class Screen(val route:String){
    object SplashScreen:Screen(route = "Splash_screen")
    object SignInScreen:Screen(route = "SignIn_screen")
    object TodoListScreen:Screen(route = "TodoList_screen")

}
