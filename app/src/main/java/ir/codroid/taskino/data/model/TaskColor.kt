package ir.codroid.taskino.data.model

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import ir.codroid.taskino.R
import ir.codroid.taskino.ui.theme.MediumGray
import ir.codroid.taskino.ui.theme.highPriorityColor
import ir.codroid.taskino.ui.theme.lowPriorityColor
import ir.codroid.taskino.ui.theme.mediumPriorityColor
import ir.codroid.taskino.ui.theme.nonePriorityColor
import ir.codroid.taskino.ui.theme.radioBlue
import ir.codroid.taskino.ui.theme.radioDarkBlue
import ir.codroid.taskino.ui.theme.radioDarkPurple
import ir.codroid.taskino.ui.theme.radioGreen
import ir.codroid.taskino.ui.theme.radioOrange
import ir.codroid.taskino.ui.theme.radioPink
import ir.codroid.taskino.ui.theme.radioPurple
import ir.codroid.taskino.ui.theme.radioRed
import ir.codroid.taskino.ui.theme.radioYellow

enum class TaskColor(val color: Color) {
    DEFAULT(MediumGray),
    ORANGE(radioOrange),
    RED(radioRed),
    PINK(radioPink),
    PURPLE(radioPurple),
    DARK_PURPLE(radioDarkPurple),
    DARK_BLUE(radioDarkBlue),
    BLUE(radioBlue),
    GREEN(radioGreen),
    YELLOW(radioYellow),
}