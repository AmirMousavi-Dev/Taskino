package ir.codroid.taskino.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

// region Priority Color
val highPriorityColor = Color(0xFFFF4646)
val mediumPriorityColor = Color(0xFFFFC114)
val lowPriorityColor = Color(0xFF00C980)
val nonePriorityColor = Color(0xFFFFFFFF)
// endregion Priority Color

// region Top Appbar Color
val ColorScheme.topAppbarColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple40
val ColorScheme.topAppbarContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else Color.White
// endregion Top Appbar Color

// region List Item  Color
val ColorScheme.listItemBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGray else Color.White
val ColorScheme.listItemTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else DarkGray
// endregion List Item Background Color

val ColorScheme.splashBGColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGray else Color.White

