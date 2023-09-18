package ir.codroid.taskino.presentation.todo_tasks.component

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ir.codroid.taskino.MainActivity
import ir.codroid.taskino.R
import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.ui.component.ChangeLanguageAlertDialog
import ir.codroid.taskino.ui.component.DisplayAlertDialog
import ir.codroid.taskino.ui.component.PriorityItem
import ir.codroid.taskino.ui.theme.DISABLE_ALPHA
import ir.codroid.taskino.ui.theme.MEDIUM_ALPHA
import ir.codroid.taskino.ui.theme.TOP_APP_BAR_HEIGHT
import ir.codroid.taskino.ui.theme.topAppbarColor
import ir.codroid.taskino.ui.theme.topAppbarContentColor
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Constants.APPLICATION_PACKAGE_NAME
import ir.codroid.taskino.util.Constants.CAFE_BAZAR_DEVELOPER_ID
import ir.codroid.taskino.util.Constants.CAFE_BAZAR_PACKAGE_NAME
import ir.codroid.taskino.util.Language
import ir.codroid.taskino.util.SearchAppbarState

@Composable
fun ListAppbar(
    searchText :String,
    userLanguage: Language,
    searchTodoTask: (String) -> Unit,
    onSortClicked: (Priority) -> Unit,
    searchAppbarState: SearchAppbarState,
    onUserLanguageClicked : (Language) ->Unit,
    onSearchClicked : () ->Unit,
    onDelete: () -> Unit
) {
    val activity = LocalContext.current as Activity
    when (searchAppbarState) {
        SearchAppbarState.CLOSED -> {
            DefaultListAppbar(
                userLanguage = userLanguage,
                onLanguageClicked = { language ->
                    onUserLanguageClicked(language)
                },
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirm = onDelete
            )
        }

        else -> {
            SearchAppbar(
                text = searchText,
                onTextChanged = { newText ->
                    searchTodoTask(newText)
                },
                onCloseClicked = onSearchClicked,
                onSearchClicked = {
                    searchTodoTask(it)
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppbar(
    userLanguage: Language,
    onLanguageClicked: (Language) -> Unit,
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirm: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.tasks)) },
        actions = {
            ListAppbarAction(
                userLanguage = userLanguage,
                onLanguageClicked = { language ->
                    onLanguageClicked(language)
                },
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirm = onDeleteAllConfirm
            )
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
    userLanguage: Language,
    onLanguageClicked: (Language) -> Unit,
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirm: () -> Unit
) {
    var openDeleteDialog by remember { mutableStateOf(false) }
    var openLanguageDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    DisplayAlertDialog(
        title = stringResource(id = R.string.ad_delete_tasks_title),
        message = stringResource(id = R.string.ad_delete_tasks_message),
        openDialog = openDeleteDialog,
        onDismiss = { openDeleteDialog = false }) {
        onDeleteAllConfirm()
    }

    ChangeLanguageAlertDialog(
        openDialog = openLanguageDialog,
        userLanguage = userLanguage,
        onDismiss = { openLanguageDialog = false },
        onConfirm = { language ->
            onLanguageClicked(language)
        }
    )
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)

    MoreAction(
        onDelete = { openDeleteDialog = true },
        onLanguage = {
            openLanguageDialog = true
        },
        onAbout = {
            context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("bazaar://details?id=$APPLICATION_PACKAGE_NAME")
                setPackage(CAFE_BAZAR_PACKAGE_NAME)
            })
        },
        onRate = {
            context.startActivity(Intent(Intent.ACTION_EDIT).apply {
                data = Uri.parse("bazaar://details?id=$APPLICATION_PACKAGE_NAME")
                setPackage(CAFE_BAZAR_PACKAGE_NAME)

            })
        },
        onApplications = {
            context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("bazaar://collection?slug=by_author&aid=$CAFE_BAZAR_DEVELOPER_ID")
                setPackage(CAFE_BAZAR_PACKAGE_NAME)
            })
        },
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
        Priority.values().slice(setOf(0, 2, 3)).forEach { priority ->
            DropdownMenuItem(
                text = { PriorityItem(priority) },
                onClick = {
                    onSortClicked(priority)
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun MoreAction(
    onDelete: () -> Unit,
    onLanguage: () -> Unit,
    onAbout: () -> Unit,
    onRate: () -> Unit,
    onApplications: () -> Unit,
) {

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
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.language),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                )

            },
            onClick = {
                onLanguage()
                expanded = false
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.about_us),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                )

            },
            onClick = {
                onAbout()
                expanded = false
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.rate_us),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                )

            },
            onClick = {
                onRate()
                expanded = false
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.our_application),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            onClick = {
                onApplications()
                expanded = false
            }
        )

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
                    if (text.isNotEmpty())
                        onTextChanged("")
                    else
                        onCloseClicked()
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