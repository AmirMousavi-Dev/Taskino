package ir.codroid.taskino.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.codroid.taskino.ui.theme.splashBGColor
import ir.codroid.taskino.ui.theme.topAppbarColor
import ir.codroid.taskino.util.Constants.SPLASH_SCREEN

@Composable
fun ChangeSystemUi(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val systemUiController = rememberSystemUiController()
    when (navBackStackEntry?.destination?.route) {
        SPLASH_SCREEN -> {
            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.splashBGColor)
        }
        else -> {
            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.topAppbarColor)
        }
    }
    systemUiController.apply {
        isNavigationBarVisible = false
        isSystemBarsVisible = false
    }
}