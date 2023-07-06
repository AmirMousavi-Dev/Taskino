package ir.codroid.taskino.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.codroid.taskino.ui.screen.task.TaskScreen
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.Constants.TASK_ARGUMENT_KEY
import ir.codroid.taskino.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToTaskScreen: (Action) -> Unit ,
    viewModel : SharedViewModel
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        TaskScreen(
            taskId = taskId,
            navigateToListScreen = navigateToTaskScreen ,
            viewModel = viewModel
        )

    }
}