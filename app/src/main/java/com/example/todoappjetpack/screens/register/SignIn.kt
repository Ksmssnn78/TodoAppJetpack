package com.example.todoappjetpack.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoappjetpack.Screen
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(
    navController: NavController,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val signInViewModel: SignInViewModel = hiltViewModel()

    var email by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            value = email,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                email = it
            },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation(),
            value = pass,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                pass = it
            },
            placeholder = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedButton(
            onClick = {
                signInViewModel.authSignIn(uname = email, pass = pass)

                signInViewModel.isValid.observe(lifeCycleOwner) {
                    if (it) {
                        navController.navigate(Screen.TodoListScreen.route)
                    } else {
                        Timber.e("Wrong Credential")
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign In")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignIn(navController = rememberNavController())
}
