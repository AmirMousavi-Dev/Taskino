package ir.codroid.taskino.ui.screen.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.ui.component.PriorityDropDownMenu
import ir.codroid.taskino.ui.theme.LARGE_PADDING
import ir.codroid.taskino.ui.theme.MEDIUM_PADDING

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChanged(it) },
            label = { Text(text = stringResource(id = R.string.title))},
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true
            )
        Divider(modifier =
        Modifier.height(MEDIUM_PADDING), color = MaterialTheme.colorScheme.background)
        PriorityDropDownMenu(
            priority = priority,
            onPriorityClicked = onPrioritySelected)
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            label = { Text(text = stringResource(id = R.string.description))},
            textStyle = MaterialTheme.typography.bodyLarge,


        )
    }
}

@Composable
@Preview
fun PreviewTaskContent() {
    TaskContent(
        "" , {},"" , {},Priority.HIGH , {}
    )
}