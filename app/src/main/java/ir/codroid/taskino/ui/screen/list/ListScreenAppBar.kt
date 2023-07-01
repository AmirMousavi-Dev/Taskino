package ir.codroid.taskino.ui.screen.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R

@Composable
fun ListAppbar(onSearchClicked: () -> Unit) {
    DefaultListAppbar(onSearchClicked = onSearchClicked)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppbar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.tasks)) },
        actions = {
            SearchAction(onSearchClicked = onSearchClicked)
        }
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
@Preview
fun PreviewListAppbar() {
    ListAppbar({})
}