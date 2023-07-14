package ir.codroid.taskino.navigation.destination

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.codroid.taskino.ui.screen.splash.SplashScreen
import ir.codroid.taskino.ui.screen.task.TaskScreen
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.Constants.LIST_ARGUMENT_KEY
import ir.codroid.taskino.util.Constants.LIST_SCREEN
import ir.codroid.taskino.util.Constants.SPLASH_SCREEN
import ir.codroid.taskino.util.Constants.TASK_ARGUMENT_KEY
import ir.codroid.taskino.util.Constants.TASK_SCREEN

fun NavGraphBuilder.splashComposable(
    navigateToTaskScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN
    ) {
        SplashScreen(navigateToTaskScreen)
    }
}