package ir.codroid.taskino.ui.screen.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.ui.component.DisplayAlertDialog
import ir.codroid.taskino.ui.component.PriorityItem
import ir.codroid.taskino.ui.theme.DISABLE_ALPHA
import ir.codroid.taskino.ui.theme.MEDIUM_ALPHA
import ir.codroid.taskino.ui.theme.TOP_APP_BAR_HEIGHT
import ir.codroid.taskino.ui.theme.topAppbarColor
import ir.codroid.taskino.ui.theme.topAppbarContentColor
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.SearchAppbarState
import ir.codroid.taskino.util.TrailingIconState

@Composable
fun ListAppbar(
    viewModel: SharedViewModel,
    onSearchClicked: (String) -> Unit,
    onSortClicked: (Priority) -> Unit,
    searchAppbarState: SearchAppbarState,
    onDelete: () -> Unit
) {
    val searchAppbarTextState = viewModel.searchAppbarTextState
    when (searchAppbarState) {
        SearchAppbarState.CLOSED -> {
            DefaultListAppbar(
                onSearchClicked = {
                    viewModel.searchAppbarState.value = SearchAppbarState.OPENED
                },
                onSortClicked = onSortClicked,
                onDeleteAllConfirm = onDelete
            )
        }

        else -> {
            SearchAppbar(
                text = searchAppbarTextState.value,
                onTextChanged = { newText ->
                    searchAppbarTextState.value = newText
                },
                onCloseClicked = {
                    viewModel.searchAppbarState.value = SearchAppbarState.CLOSED
                },
                onSearchClicked = {
                    onSearchClicked(it)
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppbar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirm: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.tasks)) },
        actions = {
            ListAppbarAction(
                onSearchClicked =  onSearchClicked ,
                onSortClicked = onSortClicked,
                onDeleteAllConfirm = onDeleteAllConfirm)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppbarColor,
            titleContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
            actionIconContentColor = MaterialTheme.colorScheme.topAppbarContentColor,
        ),
    )
}

@Composable
fun ListAppbarAction(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirm: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(id = R.string.ad_delete_tasks_title),
        message = stringResource(id = R.string.ad_delete_tasks_message),
        openDialog = openDialog.value,
        onDismiss = { openDialog.value = false }) {
        onDeleteAllConfirm()
    }
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)

    MoreAction(onDelete = {openDialog.value = true})
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
fun MoreAction(onDelete: () -> Unit) {

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
            text = {
                Text(
                    text = stringResource(id = R.string.delete_all),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            onClick = {
                onDelete()
                expanded = false
            })

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppbar(
    text: String,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.topAppbarColor
    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChanged(it)
            }, colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colorScheme.topAppbarColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    color = Color.White,
                    modifier = Modifier.alpha(MEDIUM_ALPHA)
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.topAppbarContentColor,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search),
                    tint = Color.White,
                    modifier = Modifier.alpha(DISABLE_ALPHA)
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty())
                                onTextChanged("")
                            else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }
                        }

                        TrailingIconState.READY_TO_DELETE -> {
                            onTextChanged("")
                            trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close),
                        tint = MaterialTheme.colorScheme.topAppbarContentColor,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ))
    }
}