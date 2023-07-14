package ir.codroid.taskino.navigation

import androidx.navigation.NavHostController
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.Constants.SPLASH_SCREEN
import ir.codroid.taskino.util.Constants.TASK_SCREEN

class Screen (navController: NavHostController) {
    val splash : () -> Unit = {
        navController.navigate("list/${Action.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) {inclusive = true}
        }
    }

    val list : (Action) -> Unit = {action ->
            navController.navigate("list/${action.name}") {
                popUpTo(TASK_SCREEN) {inclusive = true}
            }
    }
    val task : (Int) -> Unit = {taskId ->
        navController.navigate("task/$taskId")
    }
}