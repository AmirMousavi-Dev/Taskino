package ir.codroid.taskino.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import ir.codroid.taskino.ui.screen.splash.SplashScreen
import ir.codroid.taskino.util.Constants.SPLASH_SCREEN

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splashComposable(
    navigateToTaskScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN,
        enterTransition = {
            slideInVertically(
                initialOffsetY = {fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 300
                )
            )
        },
        exitTransition = {
            slideOutVertically(
                targetOffsetY = {fullHeight -> -fullHeight },
                animationSpec = tween(
                    durationMillis = 300
                )
            )
        }
    ) {
        SplashScreen(navigateToTaskScreen)
    }
}