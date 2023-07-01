package ir.codroid.taskino.ui.screen.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.ui.component.PriorityItem
import ir.codroid.taskino.ui.theme.LARGE_PADDING
import ir.codroid.taskino.ui.theme.topAppbarColor
import ir.codroid.taskino.ui.theme.topAppbarContentColor

@Composable
fun ListAppbar(onSearchClicked: () -> Unit, onSortClicked: (Priority) -> Unit , onDelete : () -> Unit) {
    DefaultListAppbar(onSearchClicked = onSearchClicked, onSortClicked = onSortClicked ,onDelete = onDelete)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppbar(onSearchClicked: () -> Unit, onSortClicked: (Priority) -> Unit , onDelete : () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.tasks)) },
        actions = {
            SearchAction(onSearchClicked = onSearchClicked)
            SortAction(onSortClicked = onSortClicked)
            MoreAction(onDelete = onDelete)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppbarColor ,
            titleContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
            actionIconContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
        )
    )
}

@Composable
fun SearchAction(onSearchClicked: () -> Unit) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_task)
        )
    }
}

@Composable
fun SortAction(onSortClicked: (Priority) -> Unit) {

    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = stringResource(id = R.string.sort_list)
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = { PriorityItem(Priority.LOW) },
            onClick = {
                onSortClicked(Priority.LOW)
                expanded = false
            })

        DropdownMenuItem(
            text = { PriorityItem(Priority.HIGH) },
            onClick = {
                onSortClicked(Priority.HIGH)
                expanded = false
            })

        DropdownMenuItem(
            text = { PriorityItem(Priority.NONE) },
            onClick = {
                onSortClicked(Priority.NONE)
                expanded = false
            })

    }
}

@Composable
fun MoreAction (onDelete : () -> Unit) {

    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = stringResource(id = R.string.more)
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = { Text(
                text = stringResource(id = R.string.delete_all),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
            )},
            onClick = {
                onDelete()
                expanded = false
            })

    }
}

@Composable
@Preview
fun PreviewListAppbar() {
    ListAppbar({}, {} , {})
}