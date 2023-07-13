package ir.codroid.taskino.ui.screen.list

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
        viewModel.getAllTask()
    }
    val tasks by viewModel.taskList.collectAsState()
    val action by viewModel.action
    val snackBarHostState = remember { SnackbarHostState() }
    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        taskTitle = viewModel.title.value,
        action = action
    ) {
        viewModel.handleDatabaseAction(action = action)
    }

    Scaffold(
        topBar = {
            ListAppbar(
                viewModel = viewModel,
                onSearchClicked = {},
                onSortClicked = {},
                onDelete = {})
        },
        content = {
            ListContent(
                tasks = tasks,
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
    handleDatabaseAction: () -> Unit
) {
    handleDatabaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION)
            scope.launch {
                snackBarHostState.showSnackbar(
                    "${action.name} : $taskTitle",
                    actionLabel = "ok",
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
            }
    }
}