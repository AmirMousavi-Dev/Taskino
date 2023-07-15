package ir.codroid.taskino.ui.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import ir.codroid.taskino.ui.viewmodel.SharedViewModel
import ir.codroid.taskino.util.Constants.USER_LANGUAGE
import kotlinx.coroutines.runBlocking

@Composable
fun AppConfig(
    viewModel: SharedViewModel = hiltViewModel()
) {

            with(getViewModelVariable(viewModel)){
                Log.e("3169" , "ac $languageCode")
                USER_LANGUAGE = languageCode
                viewModel.updateUserLanguage(this)


    }
}

private fun getViewModelVariable(viewModel: SharedViewModel) =
    viewModel.readUserLanguage()
