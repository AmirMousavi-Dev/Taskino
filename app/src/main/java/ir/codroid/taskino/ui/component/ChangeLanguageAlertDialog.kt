package ir.codroid.taskino.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.codroid.taskino.R
import ir.codroid.taskino.util.Language


@Composable
fun ChangeLanguageAlertDialog(
    openDialog: Boolean,
    userLanguage: Language,
    onDismiss: () -> Unit,
    onConfirm: (Language) -> Unit,
) {
    var languageState by remember { mutableStateOf(userLanguage) }
    if (openDialog)
        AlertDialog(
            text = {
                LanguageRadioButton(userLanguage = userLanguage, onButton = { language ->
                    languageState = language
                })
            },
            title = {
                Text(
                    text = stringResource(id = R.string.language),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            onDismissRequest = {
                onDismiss()
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(languageState)
                    onDismiss()
                }) {
                    Text(
                        text = stringResource(id = R.string.yes),
                    )
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    onDismiss()
                }) {
                    Text(
                        text = stringResource(id = R.string.no),
                    )
                }
            },
        )
}

@Composable
fun LanguageRadioButton(
    userLanguage: Language,
    onButton: (Language) -> Unit
) {
    var selectedItem by remember { mutableStateOf(userLanguage) }

    Column(modifier = Modifier.selectableGroup()) {
        Language.values().forEach { language ->
            Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.clickable {
                selectedItem = language
                onButton(language)
            }) {
                RadioButton(selected = selectedItem == language,
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = {
                        selectedItem = language
                        onButton(language)
                    })
                Text(
                    text = stringResource(id = language.title),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
            }

        }
    }
}