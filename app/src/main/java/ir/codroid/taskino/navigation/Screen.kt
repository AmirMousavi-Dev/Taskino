package ir.codroid.taskino.navigation

import androidx.navigation.NavHostController
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.Constants.TASK_SCREEN

class Screen (navController: NavHostController) {
    val list : (Action) -> Unit = {action ->
            navController.navigate("list/${action.name}") {
                popUpTo(TASK_SCREEN) {inclusive = true}
            }
    }
    val task : (Int) -> Unit = {taskId ->
        navController.navigate("task/$taskId")
    }
}