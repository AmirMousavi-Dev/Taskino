@file:OptIn(ExperimentalMaterial3Api::class)

package ir.codroid.taskino.ui.screen.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import ir.codroid.taskino.ui.theme.LARGE_PADDING
import ir.codroid.taskino.ui.theme.LIST_ITEM_ELEVATION
import ir.codroid.taskino.ui.theme.PRIORITY_INDICATOR_SIZE
import ir.codroid.taskino.ui.theme.listItemBackgroundColor
import ir.codroid.taskino.ui.theme.listItemTextColor

@Composable
fun ListContent() {

}

@Composable
fun ListItem(
    toDoTask: ToDoTask, navigationToTaskScreen: (taskId: Int) -> Unit
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
                        .weight(1f) ,
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
        0, "This is Title for test title design", "Some Task To DO , please do that , this text is for testing description design so I have to write some random text here .", Priority.HIGH
    ), navigationToTaskScreen = {})
}