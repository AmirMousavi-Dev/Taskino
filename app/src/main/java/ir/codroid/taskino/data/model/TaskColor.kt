package ir.codroid.taskino.data.model

import androidx.compose.ui.graphics.Color
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

enum class TaskColor(val color: Color) {
    DEFAULT_COLOR(MediumGray),
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