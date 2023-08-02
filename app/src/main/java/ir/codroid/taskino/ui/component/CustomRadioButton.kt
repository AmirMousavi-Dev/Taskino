package ir.codroid.taskino.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.TaskColor

@Composable
fun CustomRadioButton(
    isSelected: Boolean,
    taskColor: TaskColor,
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier,
        painter = painterResource(
            id = if (isSelected)
                R.drawable.radio_button_checked else
                R.drawable.radio_button_unchecked
        ),

        contentDescription = null,
        tint = taskColor.color
    )
}
