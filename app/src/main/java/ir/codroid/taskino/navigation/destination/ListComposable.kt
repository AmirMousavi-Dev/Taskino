package ir.codroid.taskino.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import ir.codroid.taskino.presentation.todo_tasks.ListScreen
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Action
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
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}