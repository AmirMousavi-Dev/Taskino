package ir.codroid.taskino.ui.screen.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import ir.codroid.taskino.ui.theme.topAppbarColor
import ir.codroid.taskino.ui.theme.topAppbarContentColor
import ir.codroid.taskino.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAppbar(
    navigateToListScreen : (Action) -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Add new Task")
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppbarColor,
            titleContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
            actionIconContentColor = MaterialTheme.colorScheme.topAppbarContentColor
        ),
        actions = {
            BackAction(onBackClick = navigateToListScreen)
        }

    )
}

@Composable
fun BackAction(
    onBackClick: (Action) -> Unit
) {
    IconButton(onClick = { onBackClick(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
    }
}