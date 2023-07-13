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
    viewModel: SharedViewModel,
    navigateToTaskScreen: (Int) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.getAllTasks()
    }
    val context = LocalContext.current
    val allTasks by viewModel.allTasks.collectAsState()
    val searchedTasks by viewModel.searchedTasks.collectAsState()
    val action by viewModel.action
    val snackBarHostState = remember { SnackbarHostState() }
    val searchAppbarState = viewModel.searchAppbarState.value
    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        taskTitle = viewModel.title.value,
        action = action,
        context = context,
        onUndoClicked = {
            viewModel.action.value = it
        }
    ) {
        viewModel.handleDatabaseAction(action = action)
    }

    Scaffold(
        topBar = {
            ListAppbar(
                viewModel = viewModel,
                onSearchClicked = {
                    viewModel.getSearchedTasks(it)
                },
                onSortClicked = {},
                searchAppbarState = searchAppbarState,
                onDelete = {
                    viewModel.action.value = Action.DELETE_ALL
                })
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchTasks = searchedTasks,
                searchAppbarState = searchAppbarState,
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
    handleDatabaseAction: () -> Unit
) {
    handleDatabaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION)
            scope.launch {
                val snackBarResult = snackBarHostState.showSnackbar(
                    setSBMessage(action , taskTitle , context),
                    actionLabel = context.getString(setSBActionLabel(action)),
                    withDismissAction = false,
                    duration = SnackbarDuration.Short
                )
                undoDeletedTask(action, snackBarResult) {
                    onUndoClicked(it)
                }
            }
    }
}

private fun setSBActionLabel(action: Action) = if (action == Action.DELETE) R.string.undo else R.string.ok
private fun setSBMessage(action: Action , taskTitle: String , context: Context)
= if (action == Action.DELETE_ALL) context.getString( R.string.delete_all_tasks) else "${action.title} : $taskTitle"

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}