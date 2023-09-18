@file:OptIn(ExperimentalMaterial3Api::class)

package ir.codroid.taskino.presentation.todo_tasks.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R
import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.model.TaskColor
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.ui.theme.LARGEST_PADDING
import ir.codroid.taskino.ui.theme.LARGE_PADDING
import ir.codroid.taskino.ui.theme.LIST_ITEM_ELEVATION
import ir.codroid.taskino.ui.theme.PRIORITY_INDICATOR_SIZE
import ir.codroid.taskino.ui.theme.TOP_PADDING
import ir.codroid.taskino.ui.theme.highPriorityColor
import ir.codroid.taskino.ui.theme.listItemBackgroundColor
import ir.codroid.taskino.ui.theme.listItemTextColor
import ir.codroid.taskino.util.Action
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListContent(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (ToDoTask) -> Unit,
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (ToDoTask) -> Unit,
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
            var itemAppeared by remember { mutableStateOf(false) }
            if (isDismiss && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDelete(task)
                }
            }
            val degrees by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0f
                else -45f
            )
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismiss,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis =
                        500
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        RedBackground(degrees = degrees)
                    },
                    dismissContent = {
                        ListItem(toDoTask = task, navigationToTaskScreen = navigationToTaskScreen)
                    }
                )
            }

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
                    style = MaterialTheme.typography.titleLarge,
                    color =
                    if (toDoTask.color == TaskColor.DEFAULT_COLOR) MaterialTheme.colorScheme.listItemTextColor
                    else toDoTask.color.color,
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
                style = MaterialTheme.typography.titleSmall,
                color =
                if (toDoTask.color == TaskColor.DEFAULT_COLOR) MaterialTheme.colorScheme.listItemTextColor
                else toDoTask.color.color,
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
        Priority.HIGH,
        TaskColor.DEFAULT_COLOR
    ), navigationToTaskScreen = {})
}