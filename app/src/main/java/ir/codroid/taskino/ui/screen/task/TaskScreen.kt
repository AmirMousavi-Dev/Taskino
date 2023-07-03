@file:OptIn(ExperimentalMaterial3Api::class)

package ir.codroid.taskino.ui.screen.task

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import ir.codroid.taskino.util.Action

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen (
    navigateToListScreen: (Action) -> Unit
) {
    Scaffold(
        topBar = {
                 TaskAppbar(navigateToListScreen = navigateToListScreen )
        } ,
        content = {}
    )
}