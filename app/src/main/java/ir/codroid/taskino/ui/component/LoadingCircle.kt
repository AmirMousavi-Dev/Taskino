package ir.codroid.taskino.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import ir.codroid.taskino.R

@Composable
fun LoadingCircle(isSystemDark: Boolean) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            if (isSystemDark) R.raw.dark_loading else R.raw.light_loading
        )
    )
    LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
}