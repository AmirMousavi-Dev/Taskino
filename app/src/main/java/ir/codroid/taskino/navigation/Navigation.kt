package ir.codroid.taskino.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ir.codroid.taskino.navigation.destination.listComposable
import ir.codroid.taskino.navigation.destination.taskComposable
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(
    viewModel : SharedViewModel = hiltViewModel(),
    navController: NavHostController) {
    val screen = remember(navController) {
        Screen(navController)
    }
    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable(viewModel = viewModel ,screen.task)
        taskComposable(viewModel = viewModel ,screen.list)
    }
}