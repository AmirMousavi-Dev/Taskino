package ir.codroid.taskino.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.TaskColor
import ir.codroid.taskino.ui.theme.MEDIUM_ALPHA
import ir.codroid.taskino.ui.theme.PRIORITY_DROP_DOWN_MENU_HEIGHT
import ir.codroid.taskino.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityDropDownMenu(
    priority: Priority,
    taskColor: TaskColor,
    onPriorityClicked: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val angle: Float by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                parentSize = it.size
            }
            .height(PRIORITY_DROP_DOWN_MENU_HEIGHT)
            .clickable { expanded = !expanded }
            .border(
                width = 1.dp,
                color = if (taskColor == TaskColor.DEFAULT_COLOR)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = MEDIUM_ALPHA)
                else taskColor.color.copy(alpha = MEDIUM_ALPHA),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        ) {
            drawCircle(priority.color)
        }

        Text(
            text = stringResource(id = priority.title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(8f)
        )
        IconButton(
            onClick = {
                expanded = !expanded
            },
            modifier = Modifier
                .alpha(MEDIUM_ALPHA)
                .rotate(angle)
                .weight(1.5f)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = R.string.drop_down_arrow_icon)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { parentSize.width.toDp() })
        ) {
            Priority.values().slice(0..2).forEach { priority ->
                DropdownMenuItem(
                    text = { PriorityItem(priority = priority) },
                    onClick = {
                        onPriorityClicked(priority)
                        expanded = false
                    })
            }
        }
    }

}

@Composable
@Preview
fun PreviewPriorityDropDownMenu() {
    PriorityDropDownMenu(priority = Priority.LOW,TaskColor.DEFAULT_COLOR, onPriorityClicked = {})
}