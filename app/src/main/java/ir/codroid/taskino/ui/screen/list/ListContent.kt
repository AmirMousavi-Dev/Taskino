@file:OptIn(ExperimentalMaterial3Api::class)

package ir.codroid.taskino.ui.screen.list

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.ui.component.LoadingCircle
import ir.codroid.taskino.ui.theme.LARGEST_PADDING
import ir.codroid.taskino.ui.theme.LARGE_PADDING
import ir.codroid.taskino.ui.theme.LIST_ITEM_ELEVATION
import ir.codroid.taskino.ui.theme.PRIORITY_INDICATOR_SIZE
import ir.codroid.taskino.ui.theme.TOP_PADDING
import ir.codroid.taskino.ui.theme.highPriorityColor
import ir.codroid.taskino.ui.theme.listItemBackgroundColor
import ir.codroid.taskino.ui.theme.listItemTextColor
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.RequestState
import ir.codroid.taskino.util.SearchAppbarState

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    searchTasks: RequestState<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    searchAppbarState: SearchAppbarState,
    sortState: RequestState<Priority>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {

    if (sortState is RequestState.Success) {
        when {
            searchAppbarState == SearchAppbarState.TRIGGERED -> {
                if (searchTasks is RequestState.Success)
                    HandleListContent(
                        tasks = searchTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigationToTaskScreen = navigationToTaskScreen
                    )
            }

            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success)
                    HandleListContent(
                        tasks = allTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigationToTaskScreen = navigationToTaskScreen
                    )
            }

            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigationToTaskScreen = navigationToTaskScreen
                )
            }

            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigationToTaskScreen = navigationToTaskScreen
                )
            }
        }
    }
}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty())
        EmptyList()
    else
        DisplayTasks(
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigationToTaskScreen = navigationToTaskScreen
        )
}

@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
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
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismiss = dismissState.isDismissed(DismissDirection.EndToStart)
            if (isDismiss && dismissDirection == DismissDirection.EndToStart)
                onSwipeToDelete(Action.DELETE, task)
            val degrees by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0f
                else -45f
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),

                background = { RedBackground(degrees = degrees) },
                dismissContent = {
                    ListItem(toDoTask = task, navigationToTaskScreen = navigationToTaskScreen)

                })

        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(highPriorityColor)
            .padding(LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete),
            tint = Color.White,
            modifier = Modifier.rotate(degrees = degrees)
        )
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