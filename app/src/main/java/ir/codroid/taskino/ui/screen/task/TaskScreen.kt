@file:OptIn(ExperimentalMaterial3Api::class)

package ir.codroid.taskino.ui.screen.task

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.ui.component.LoadingCircle
import ir.codroid.taskino.ui.viewmodel.TaskScreenViewModel
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.RequestState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    viewModel: TaskScreenViewModel = hiltViewModel(),
    taskId: Int,
    navigateToListScreen: (Action) -> Unit
) {
    LaunchedEffect(key1 = true){
        viewModel.getSelectedTask(taskId)
    }
    val task by viewModel.selectedTask.collectAsState()
    when (task) {
        is RequestState.NotInitialize -> {
        }
        is RequestState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingCircle(isSystemDark = isSystemInDarkTheme())
            }
        }

        is RequestState.Success -> {
            val selectedTask = (task as RequestState.Success<ToDoTask?>).data
            Scaffold(
                topBar = {

                    TaskAppbar(
                        task = selectedTask, navigateToListScreen = navigateToListScreen
                    )
                },
                content = {}
            )
        }

        is RequestState.Error -> {

        }
    }

}