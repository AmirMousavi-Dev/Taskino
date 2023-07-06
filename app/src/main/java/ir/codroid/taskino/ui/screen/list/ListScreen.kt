package ir.codroid.taskino.ui.screen.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import ir.codroid.taskino.R
import ir.codroid.taskino.ui.viewmodel.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    viewModel : SharedViewModel,
    navigateToTaskScreen: (Int) -> Unit
) {
    LaunchedEffect(key1 = true){
        viewModel.getAllTask()
    }
    val tasks by viewModel.taskList.collectAsState()
    val action by viewModel.action
    viewModel.handleDatabaseAction(action)


    Scaffold(
        topBar = { ListAppbar(
            viewModel = viewModel,
            onSearchClicked = {},
            onSortClicked = {},
            onDelete = {})
        },
        content = {
            ListContent(
                tasks = tasks ,
                navigationToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = { ListScreenFab(onFabClick = navigateToTaskScreen) }
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