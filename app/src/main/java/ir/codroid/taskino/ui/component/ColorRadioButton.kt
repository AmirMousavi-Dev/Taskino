package ir.codroid.taskino.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ir.codroid.taskino.data.model.TaskColor
import ir.codroid.taskino.ui.theme.COLOR_RADIO_GROUP_HEIGHT
import ir.codroid.taskino.ui.theme.DISABLE_ALPHA
import ir.codroid.taskino.ui.theme.DarkGray
import ir.codroid.taskino.ui.theme.MediumGray
import ir.codroid.taskino.ui.theme.radioBlue
import ir.codroid.taskino.ui.theme.radioDarkBlue
import ir.codroid.taskino.ui.theme.radioDarkPurple
import ir.codroid.taskino.ui.theme.radioGreen
import ir.codroid.taskino.ui.theme.radioOrange
import ir.codroid.taskino.ui.theme.radioPink
import ir.codroid.taskino.ui.theme.radioPurple
import ir.codroid.taskino.ui.theme.radioRed
import ir.codroid.taskino.ui.theme.radioYellow


@Composable
fun ColorRadioButton(
    taskColor: TaskColor,
    taskColorSelected: (TaskColor) -> Unit
) {
    val colors = listOf(
        TaskColor.DEFAULT,
        TaskColor.ORANGE,
        TaskColor.RED,
        TaskColor.PINK,
        TaskColor.PURPLE,
        TaskColor.DARK_PURPLE,
        TaskColor.DARK_BLUE,
        TaskColor.BLUE,
        TaskColor.GREEN,
        TaskColor.YELLOW,

        )
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
                color = if (taskColor == TaskColor.DEFAULT)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLE_ALPHA)
                else taskColor.color.copy(alpha = DISABLE_ALPHA),
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