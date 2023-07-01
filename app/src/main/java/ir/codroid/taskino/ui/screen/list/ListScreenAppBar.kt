package ir.codroid.taskino.ui.screen.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R

@Composable
fun ListAppbar () {
    DefaultListAppbar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppbar() {
    SmallTopAppBar(
        title = { Text(text = stringResource(id = R.string.tasks))}
    )
}

@Composable
@Preview
fun PreviewListAppbar () {
    ListAppbar()
}