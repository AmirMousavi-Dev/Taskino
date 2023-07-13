@file:OptIn(ExperimentalMaterial3Api::class)

package ir.codroid.taskino.ui.screen.list

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.ui.component.LoadingCircle
import ir.codroid.taskino.ui.theme.LARGE_PADDING
import ir.codroid.taskino.ui.theme.LIST_ITEM_ELEVATION
import ir.codroid.taskino.ui.theme.PRIORITY_INDICATOR_SIZE
import ir.codroid.taskino.ui.theme.TOP_PADDING
import ir.codroid.taskino.ui.theme.listItemBackgroundColor
import ir.codroid.taskino.ui.theme.listItemTextColor
import ir.codroid.taskino.util.RequestState
import ir.codroid.taskino.util.SearchAppbarState

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    searchTasks: RequestState<List<ToDoTask>>,
    searchAppbarState: SearchAppbarState,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {

    if (searchAppbarState == SearchAppbarState.TRIGGERED)
        when (searchTasks) {
            is RequestState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingCircle(isSystemDark = isSystemInDarkTheme())
                }
            }

            is RequestState.Success -> {
                HandleListContent(
                    tasks = searchTasks.data,
                    navigationToTaskScreen = navigationToTaskScreen
                )
            }

            is RequestState.NotInitialize -> {
                Log.e("List_Screen", "data is not initialize")
            }

            is RequestState.Error -> {
                Log.e("List_Screen", searchTasks.error.message ?: "task screen error")
            }
        }
    else
        when (allTasks) {
            is RequestState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingCircle(isSystemDark = isSystemInDarkTheme())
                }
            }

            is RequestState.Success -> {
                HandleListContent(
                    tasks = allTasks.data,
                    navigationToTaskScreen = navigationToTaskScreen
                )
            }

            is RequestState.NotInitialize -> {
                Log.e("List_Screen", "data is not initialize")
            }

            is RequestState.Error -> {
                Log.e("List_Screen", allTasks.error.message ?: "task screen error")
            }
        }


}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty())
        EmptyList()
    else
        DisplayTasks(tasks = tasks, navigationToTaskScreen = navigationToTaskScreen)
}

@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = TOP_PADDING)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.listItemBackgroundColor)
    ) {
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ) { task ->
            ListItem(toDoTask = task, navigationToTaskScreen = navigationToTaskScreen)
        }
    }
}

@Composable
fun ListItem(
    toDoTask: ToDoTask,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.listItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = LIST_ITEM_ELEVATION,
        onClick = {
            navigationToTaskScreen(toDoTask.id)
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(8f),
                    text = toDoTask.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.listItemTextColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(toDoTask.priority.color)
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.listItemTextColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Composable
@Preview
fun PreviewListContent() {
    ListItem(toDoTask = ToDoTask(
        0,
        "This is Title for test title design",
        "Some Task To DO , please do that , this text is for testing description design so I have to write some random text here .",
        Priority.HIGH
    ), navigationToTaskScreen = {})
}