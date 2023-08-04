package ir.codroid.taskino

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.codroid.taskino.navigation.SetupNavigation
import ir.codroid.taskino.ui.component.AppConfig
import ir.codroid.taskino.ui.component.ChangeSystemUi
import ir.codroid.taskino.ui.theme.TaskinoTheme
import ir.codroid.taskino.util.Constants.USER_LANGUAGE
import ir.codroid.taskino.util.Language
import ir.codroid.taskino.util.LocaleUtil

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * release 1.1
     */
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskinoTheme {
                navController = rememberAnimatedNavController()

                AppConfig()
                Log.e("3169" , USER_LANGUAGE)
                LocaleUtil.setLocale(LocalContext.current , USER_LANGUAGE)
                val layoutDirection = if (USER_LANGUAGE == Language.ENGLISH.languageCode)
                    LayoutDirection.Ltr
                else
                    LayoutDirection.Rtl
                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {


                ChangeSystemUi(navController = navController)

                SetupNavigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskinoTheme {
        Greeting("Android")
    }
}