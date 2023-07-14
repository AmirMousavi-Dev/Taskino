package ir.codroid.taskino.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.AnimatedNavHost
import ir.codroid.taskino.navigation.destination.listComposable
import ir.codroid.taskino.navigation.destination.splashComposable
import ir.codroid.taskino.navigation.destination.taskComposable
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Constants.LIST_SCREEN
import ir.codroid.taskino.util.Constants.SPLASH_SCREEN

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavigation(
    viewModel : SharedViewModel = hiltViewModel(),
    navController: NavHostController) {
    val screen = remember(navController) {
        Screen(navController)
    }
    AnimatedNavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        splashComposable(screen.splash)
        listComposable(viewModel = viewModel ,screen.task)
        taskComposable(viewModel = viewModel ,screen.list)
    }
}