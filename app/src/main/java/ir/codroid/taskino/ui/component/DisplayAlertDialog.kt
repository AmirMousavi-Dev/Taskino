package ir.codroid.taskino.ui.component

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ir.codroid.taskino.R

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                ) },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                ) },
            confirmButton =
            {
                Button(onClick = {
                    onConfirm()
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
            onDismissRequest = {
                onDismiss()
            }
        )
    }
}