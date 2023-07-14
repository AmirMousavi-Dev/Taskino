package ir.codroid.taskino.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import ir.codroid.taskino.ui.screen.list.ListScreen
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Constants.LIST_ARGUMENT_KEY
import ir.codroid.taskino.util.Constants.LIST_SCREEN
import ir.codroid.taskino.util.toAction

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.listComposable(
    viewModel: SharedViewModel,
    navigateToTaskScreen: (Int) -> Unit
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        LaunchedEffect(key1 = action){
            viewModel.action.value = action
        }
        val dataBaseAction by viewModel.action
        ListScreen(
            action = dataBaseAction,
            viewModel = viewModel,
            navigateToTaskScreen = navigateToTaskScreen)
    }
}