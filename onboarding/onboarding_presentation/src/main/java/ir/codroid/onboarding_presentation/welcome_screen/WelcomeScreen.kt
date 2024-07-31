package ir.codroid.onboarding_presentation.welcome_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.codroid.core_ui.LocalSpacing
import ir.codroid.core_ui.R
import ir.codroid.core_ui.util.TaskinoPreviews

@Composable
fun WelcomeScreen(
    onClick : () -> Unit
) {

    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                onClick = {} ,
                shape = RoundedCornerShape(
                    topStart = spacing.spaceLarge ,
                    topEnd = spacing.spaceLarge),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                enabled = false
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        modifier = Modifier
                            .padding(spacing.spaceMedium),
                        onClick = {
                            onClick()
                        }) {
                        Text(text = stringResource(id = ir.codroid.core.R.string.lets_go))
                    }

                Column(modifier = Modifier.fillMaxSize() ,
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center) {

                    Text(
                    text = stringResource(id = ir.codroid.core.R.string.welcome_to_taskino),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            spacing.spaceMedium
                        ))

                    Text(
                        text = stringResource(id = ir.codroid.core.R.string.welcome_message),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                spacing.spaceMedium
                            ))

                }
                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = spacing.spaceExtraLarge) ,
            contentAlignment = Alignment.TopCenter) {
            Column{
                Image(
                    painter = painterResource(id = R.drawable.img_wellcome_screen),
                    contentDescription = stringResource(id = ir.codroid.core.R.string.welcome_to_taskino),
                    modifier = Modifier.size(300.dp)
                )
            }
        }
    }
}


@TaskinoPreviews
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(){

    }
}