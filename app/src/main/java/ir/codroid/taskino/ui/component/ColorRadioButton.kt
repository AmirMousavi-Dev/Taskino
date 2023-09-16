package ir.codroid.taskino.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ir.codroid.taskino.domain.model.TaskColor
import ir.codroid.taskino.ui.theme.COLOR_RADIO_GROUP_HEIGHT
import ir.codroid.taskino.ui.theme.MEDIUM_ALPHA


@Composable
fun ColorRadioButton(
    taskColor: TaskColor,
    taskColorSelected: (TaskColor) -> Unit
) {
    val colors = TaskColor.values()
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                parentSize = it.size
            }
            .height(COLOR_RADIO_GROUP_HEIGHT)
            .selectableGroup()
            .border(
                width = 1.dp,
                color = if (taskColor == TaskColor.DEFAULT_COLOR)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = MEDIUM_ALPHA)
                else taskColor.color.copy(alpha = MEDIUM_ALPHA),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        colors.forEach { radioColor ->
            CustomRadioButton(
                isSelected = taskColor == radioColor,
                taskColor = radioColor,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .selectable(
                        selected = (taskColor == radioColor),
                        onClick = {
                            taskColorSelected(radioColor)
                        },
                        role = Role.RadioButton
                    )
            )

        }
    }
}