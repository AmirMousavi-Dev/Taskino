package ir.codroid.taskino.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ir.codroid.taskino.navigation.destination.listComposable
import ir.codroid.taskino.navigation.destination.taskComposable
import ir.codroid.taskino.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(navController: NavHostController) {
    val screen = remember(navController) {
        Screen(navController)
    }
    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable(screen.task)
        taskComposable(screen.list)
    }
}