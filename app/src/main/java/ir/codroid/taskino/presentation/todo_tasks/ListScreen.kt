package ir.codroid.taskino.presentation.todo_tasks

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ir.codroid.taskino.MainActivity
import ir.codroid.taskino.R
import ir.codroid.taskino.presentation.todo_tasks.component.ListAppbar
import ir.codroid.taskino.presentation.todo_tasks.component.ListContent
import ir.codroid.taskino.util.Action
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    viewModel: TodoTasksViewModel = hiltViewModel(),
    navigateToTaskScreen: (Int) -> Unit
) {

    val state = viewModel.state.collectAsState()

    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        message = viewModel.recentlyDeletedTask?.title,
        actionLabel = "action",
        onClicked = {
            viewModel.onEvent(TodoTasksEvent.RestoreTodoTask)
        }
    )

    Scaffold(
        topBar = {
            ListAppbar(
                searchText = state.value.searchQuery,
                onSearchClicked = {
                    viewModel.onEvent(TodoTasksEvent.ToggleSearch)
                },
                onSortClicked = { priority ->
                    viewModel.onEvent(TodoTasksEvent.Order(priority))
                },
                searchAppbarState = state.value.searchAppBaseState,
                userLanguage = state.value.language,
                searchTodoTask = { searchQuery ->
                    viewModel.onEvent(TodoTasksEvent.SearchTodoTask(searchQuery))
                },
                onUserLanguageClicked = { language ->
                    viewModel.onEvent(TodoTasksEvent.SelectLanguage(language))
                    val activity = context as Activity
                    activity.apply {
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                },
                onDelete = {
                    viewModel.onEvent(TodoTasksEvent.DeleteAllTodoTasks)
                })
        },
        content = {
            ListContent(
                tasks = state.value.todoTasks,
                onSwipeToDelete = { todoTask ->
                    viewModel.onEvent(TodoTasksEvent.DeleteTodoTask(todoTask))
                    snackBarHostState.currentSnackbarData?.dismiss()
                },
                navigationToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = { ListScreenFab(onFabClick = navigateToTaskScreen) },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    )
}

@Composable
fun ListScreenFab(onFabClick: (Int) -> Unit) {
    FloatingActionButton(onClick = { onFabClick(-1) }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.fab_add)
        )
    }
}

@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    message: String?,
    actionLabel: String,
    onClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        scope.launch {
            message?.let {

                val snackBarResult = snackBarHostState.showSnackbar(
                    message = it,
                    actionLabel = actionLabel,
                    withDismissAction = false,
                    duration = SnackbarDuration.Short
                )
                onActionClicked(snackBarResult) {
                    onClicked()
                }
            }
        }
    }
}

//private fun setSBActionLabel(action: Action) =
//    if (action == Action.DELETE) R.string.undo else R.string.ok
//
//private fun setSBMessage(event: TodoTasksEvent, taskTitle: String, context: Context) =
//    if (action == Action.DELETE_ALL) context.getString(R.string.delete_all_tasks) else context.getString(
//        action.title
//    ) + " : $taskTitle"

private fun onActionClicked(
    snackBarResult: SnackbarResult,
    onActionClicked: () -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed) {
        onActionClicked()
    }
}