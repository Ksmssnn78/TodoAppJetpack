package com.example.todoappjetpack.screens.todo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.roomtodo.models.TodoModel
import com.example.todoappjetpack.screens.customDialogue.CustomDialog
import timber.log.Timber

@Composable
fun TodoList(
    navController: NavController,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val viewModel: TodoViewModel = hiltViewModel()
    viewModel.getTaskList()
    var todoList by remember {
        mutableStateOf(listOf<TodoModel>())
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (!showDialog) {
        viewModel.taskList.observe(lifeCycleOwner) {
            if (it != null) {
                todoList = it
            }
        }
    }

    if (showDialog) {
        CustomDialog(value = "", setShowDialog = {
            showDialog = it
        }) {
            Timber.e(it)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().padding(10.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Todo Task List",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(todoList) { todos ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .border(BorderStroke(1.dp, Color.DarkGray))
                    ) {
                        Text(
                            text = todos.task,
                            modifier = Modifier.fillMaxWidth(.85f)
                        )
                        Checkbox(checked = todos.check, onCheckedChange = {
                            val isChecked = todos.check
                            val newTask =
                                TodoModel(tid = todos.tid, user = todos.user, task = todos.task, check = !isChecked)
                            viewModel.updateTask(newTask)
                        })
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
        FloatingActionButton(

            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            onClick = {
                showDialog = true
            },
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
                .padding(5.dp),
            elevation = FloatingActionButtonDefaults.elevation(10.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add FAB",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoList(navController = rememberNavController())
}
