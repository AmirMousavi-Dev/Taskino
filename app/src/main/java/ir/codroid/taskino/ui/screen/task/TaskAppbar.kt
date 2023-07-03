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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.ToDoTask
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
            DeleteAction(onAddClick = navigateToListScreen)
            UpdateAction(onAddClick = navigateToListScreen)
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
fun DeleteAction(
    onAddClick: (Action) -> Unit
) {
    IconButton(onClick = { onAddClick(Action.DELETE) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete)
        )
    }
}

@Composable
fun UpdateAction(
    onAddClick: (Action) -> Unit
) {
    IconButton(onClick = { onAddClick(Action.UPDATE) }) {
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
            0, "Codroid-ir", "some random text", Priority.HIGH
        ),
        navigateToListScreen = {})
}