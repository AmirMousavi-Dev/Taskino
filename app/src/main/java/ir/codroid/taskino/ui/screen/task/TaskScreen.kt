@file:OptIn(ExperimentalMaterial3Api::class)

package ir.codroid.taskino.ui.screen.task

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.ui.component.LoadingCircle
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.RequestState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    viewModel: SharedViewModel,
    taskId: Int,
    navigateToListScreen: (Action) -> Unit
) {
    LaunchedEffect(key1 = taskId) {
        viewModel.getSelectedTask(taskId)
    }
    BackHandler(onBackPressed = {navigateToListScreen(Action.NO_ACTION)})
    val task by viewModel.selectedTask.collectAsState()
    val title: String by viewModel.title
    val description: String by viewModel.description
    val priority: Priority by viewModel.priority
    val context = LocalContext.current

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
                        task = selectedTask, navigateToListScreen = { action ->
                            if (action == Action.NO_ACTION)
                                navigateToListScreen(action)
                            else
                                if (viewModel.validateFields())
                                    navigateToListScreen(action)
                                else
                                    displayToast(context = context)

                        }
                    )
                },
                content = {
                    LaunchedEffect(key1 = selectedTask) {
                        if (selectedTask != null || taskId == -1)
                            viewModel.updateTaskFiled(selectedTask)
                    }
                    TaskContent(
                        title = title,
                        onTitleChanged = { title ->
                            viewModel.updateTitle(title)
                        },
                        description = description,
                        onDescriptionChanged = { description ->
                            viewModel.description.value = description
                        },
                        priority = priority,
                        onPrioritySelected = { priority ->
                            viewModel.priority.value = priority
                        }
                    )
                }
            )
        }

        is RequestState.Error -> {

        }
    }

}

private fun displayToast(context: Context) {
    Toast.makeText(
        context, context.getText(R.string.fields_empty), Toast.LENGTH_SHORT
    ).show()

}

@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val callBack = remember {
        object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }

        }
    }
    
    DisposableEffect(key1 = backDispatcher){
        backDispatcher?.addCallback(callBack)
        onDispose{
            callBack.remove()
        }
    }
}
