package ir.codroid.taskino.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.codroid.taskino.ui.screen.list.ListScreen
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Constants.LIST_ARGUMENT_KEY
import ir.codroid.taskino.util.Constants.LIST_SCREEN
import ir.codroid.taskino.util.toAction

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
        ListScreen(
            viewModel = viewModel,
            navigateToTaskScreen = navigateToTaskScreen)
    }
}