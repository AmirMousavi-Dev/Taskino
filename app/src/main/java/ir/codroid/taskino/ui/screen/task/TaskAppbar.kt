@file:OptIn(ExperimentalMaterial3Api::class)

package ir.codroid.taskino.ui.screen.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R
import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.model.TaskColor
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.ui.component.DisplayAlertDialog
import ir.codroid.taskino.ui.theme.topAppbarColor
import ir.codroid.taskino.ui.theme.topAppbarContentColor
import ir.codroid.taskino.util.Action

@Composable
fun TaskAppbar(
    task: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    if (task == null)
        NewTaskAppbar(navigateToListScreen = navigateToListScreen)
    else
        ExistingTaskAppbar(selectedTask = task, navigateToListScreen = navigateToListScreen)

}

@Composable
fun NewTaskAppbar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.add_task))
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppbarColor,
            titleContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
            actionIconContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
            navigationIconContentColor = MaterialTheme.colorScheme.topAppbarContentColor
        ),
        actions = {
            AddAction(onAddClick = navigateToListScreen)
        },
        navigationIcon = {
            BackAction(onBackClick = navigateToListScreen)
        }

    )
}

@Composable
fun BackAction(
    onBackClick: (Action) -> Unit
) {
    IconButton(onClick = { onBackClick(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow)
        )
    }
}

@Composable
fun AddAction(
    onAddClick: (Action) -> Unit
) {
    IconButton(onClick = { onAddClick(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_task)
        )
    }
}

@Composable
fun ExistingTaskAppbar(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = selectedTask.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppbarColor,
            titleContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
            actionIconContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
            navigationIconContentColor = MaterialTheme.colorScheme.topAppbarContentColor
        ),
        actions = {
            ExitingTaskAppBarAction(
                selectedTask = selectedTask, navigateToListScreen = navigateToListScreen
            )
        },
        navigationIcon = {
            CloseAction(onAddClick = navigateToListScreen)
        }

    )
}

@Composable
fun CloseAction(
    onAddClick: (Action) -> Unit
) {
    IconButton(onClick = { onAddClick(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close)
        )
    }
}

@Composable
fun ExitingTaskAppBarAction(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    DisplayAlertDialog(
        title = stringResource(id = R.string.ad_delete_title, selectedTask.title),
        message = stringResource(id = R.string.ad_delete_message, selectedTask.title),
        openDialog = openDialog.value,
        onDismiss = {
            openDialog.value = false
        }) {
        navigateToListScreen(Action.DELETE)
    }
    DeleteAction {
        openDialog.value = true
    }
    UpdateAction(onUpdateClick = navigateToListScreen)
}

@Composable
fun DeleteAction(
    onDeleteClick: () -> Unit
) {
    IconButton(onClick = { onDeleteClick() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete)
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClick: (Action) -> Unit
) {
    IconButton(onClick = { onUpdateClick(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.update)
        )
    }
}

@Composable
@Preview
fun PreviewTaskAppbar() {
//    TaskAppbar(navigateToListScreen = {})

    ExistingTaskAppbar(
        ToDoTask(
            0, "Codroid-ir", "some random text", Priority.HIGH, TaskColor.DEFAULT_COLOR
        ),
        navigateToListScreen = {})
}