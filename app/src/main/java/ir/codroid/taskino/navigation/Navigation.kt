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
    navController: NavHostController ,
    viewModel: SharedViewModel = hiltViewModel()
) {
    val screen = remember(navController) {
        Screen(navController)
    }
    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable(screen.task , viewModel = viewModel)
        taskComposable(screen.list , viewModel = viewModel)
    }
}