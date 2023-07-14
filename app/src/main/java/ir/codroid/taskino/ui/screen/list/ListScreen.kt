package ir.codroid.taskino.ui.screen.list

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ir.codroid.taskino.R
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Action
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    action: Action,
    viewModel: SharedViewModel,
    navigateToTaskScreen: (Int) -> Unit
) {
    LaunchedEffect(key1 = action){
        viewModel.handleDatabaseAction(action = action)
    }
    val context = LocalContext.current
    val allTasks by viewModel.allTasks.collectAsState()
    val searchedTasks by viewModel.searchedTasks.collectAsState()
    val sortState by viewModel.sortState.collectAsState()
    val lowPriorityTasks by viewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by viewModel.highPriorityTasks.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val searchAppbarState = viewModel.searchAppbarState

    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        taskTitle = viewModel.title,
        action = action,
        context = context,
        onUndoClicked = {newAction ->
            viewModel.updateAction(newAction)
        }
    ) {newAction ->
        viewModel.updateAction(newAction)
    }

    Scaffold(
        topBar = {
            ListAppbar(
                viewModel = viewModel,
                onSearchClicked = {
                    viewModel.getSearchedTasks(it)
                },
                onSortClicked = { priority ->
                    viewModel.persistSortState(priority = priority)
                },
                searchAppbarState = searchAppbarState,
                onDelete = {
                    viewModel.updateAction(Action.DELETE_ALL)
                })
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchTasks = searchedTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                searchAppbarState = searchAppbarState,
                onSwipeToDelete = { newAction, toDoTask ->
                    viewModel.updateTaskFiled(selectedTask = toDoTask)
                    viewModel.updateAction(newAction)
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
    taskTitle: String,
    action: Action,
    context: Context,
    onUndoClicked: (Action) -> Unit,
    onCompleted: (Action) -> Unit
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackBarHostState.showSnackbar(
                    setSBMessage(action, taskTitle, context),
                    actionLabel = context.getString(setSBActionLabel(action)),
                    withDismissAction = false,
                    duration = SnackbarDuration.Short
                )
                undoDeletedTask(action, snackBarResult) {
                    onUndoClicked(it)
                }
            }
            onCompleted(Action.NO_ACTION)
        }
    }
}

private fun setSBActionLabel(action: Action) =
    if (action == Action.DELETE) R.string.undo else R.string.ok

private fun setSBMessage(action: Action, taskTitle: String, context: Context) =
    if (action == Action.DELETE_ALL) context.getString(R.string.delete_all_tasks) else context.getString(
        action.title
    ) + " : $taskTitle"

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}