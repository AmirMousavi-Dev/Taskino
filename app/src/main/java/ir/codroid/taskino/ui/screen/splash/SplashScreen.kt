package ir.codroid.taskino.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import ir.codroid.taskino.R
import ir.codroid.taskino.ui.component.LoadingCircle
import ir.codroid.taskino.ui.theme.SPLASH_LOGO_PADDING
import ir.codroid.taskino.ui.theme.splashBGColor
import ir.codroid.taskino.util.Constants.SPLASH_SCREEN_DELAY
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToTaskScreen: () -> Unit

) {
    LaunchedEffect(key1 = true){
        delay(SPLASH_SCREEN_DELAY)
        navigateToTaskScreen()
    }
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.splashBGColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.taskino), contentDescription = stringResource(
                id = R.string.app_name
            ) ,
            modifier = Modifier
                .weight(0.8f)
                .fillMaxSize()
                .padding(SPLASH_LOGO_PADDING),
        )
        Box(modifier = Modifier.weight(0.2f), contentAlignment = Alignment.BottomCenter) {

        LoadingCircle(isSystemDark = false)
    }}
}