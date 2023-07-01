package ir.codroid.taskino.data.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import ir.codroid.taskino.R
import ir.codroid.taskino.ui.theme.highPriorityColor
import ir.codroid.taskino.ui.theme.lowPriorityColor
import ir.codroid.taskino.ui.theme.mediumPriorityColor
import ir.codroid.taskino.ui.theme.nonePriorityColor

enum class Priority(val color: Color, @StringRes val title: Int) {
    HIGH(highPriorityColor, R.string.high),
    MEDIUM(mediumPriorityColor, R.string.medium),
    LOW(lowPriorityColor, R.string.low),
    NONE(nonePriorityColor, R.string.none),
}