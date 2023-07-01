package ir.codroid.taskino.data.model

import androidx.compose.ui.graphics.Color
import ir.codroid.taskino.ui.theme.highPriorityColor
import ir.codroid.taskino.ui.theme.lowPriorityColor
import ir.codroid.taskino.ui.theme.mediumPriorityColor
import ir.codroid.taskino.ui.theme.nonePriorityColor

enum class Priority(val color: Color) {
    HIGH(highPriorityColor),
    MEDIUM(mediumPriorityColor),
    LOW(lowPriorityColor),
    NONE(nonePriorityColor),
}