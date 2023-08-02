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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.TaskColor
import ir.codroid.taskino.ui.component.ColorRadioButton
import ir.codroid.taskino.ui.component.PriorityDropDownMenu
import ir.codroid.taskino.ui.theme.DISABLE_ALPHA
import ir.codroid.taskino.ui.theme.LARGE_PADDING
import ir.codroid.taskino.ui.theme.MEDIUM_PADDING
import ir.codroid.taskino.ui.theme.TOP_PADDING

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    priority: Priority,
    taskColor: TaskColor,
    onPrioritySelected: (Priority) -> Unit,
    taskColorSelected: (TaskColor) -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = LARGE_PADDING,
                end = LARGE_PADDING,
                bottom = LARGE_PADDING,
                top = TOP_PADDING + LARGE_PADDING
            )
    ) {
        if (taskColor == TaskColor.DEFAULT) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChanged(it) },
            label = { Text(text = stringResource(id = R.string.title)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true
        )
        } else{
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = taskColor.color , unfocusedBorderColor =
                taskColor.color.copy(alpha = DISABLE_ALPHA) , focusedLabelColor = taskColor.color , unfocusedLabelColor =
                taskColor.color.copy(alpha = DISABLE_ALPHA)),
                onValueChange = { onTitleChanged(it) },
                label = { Text(text = stringResource(id = R.string.title)) },
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true
            )
        }
        Divider(
            modifier =
            Modifier.height(MEDIUM_PADDING), color = MaterialTheme.colorScheme.background
        )
        PriorityDropDownMenu(
            priority = priority,
            taskColor = taskColor ,
            onPriorityClicked = onPrioritySelected
        )
        Divider(
            modifier =
            Modifier.height(MEDIUM_PADDING), color = MaterialTheme.colorScheme.background
        )
        ColorRadioButton(taskColor = taskColor , taskColorSelected = taskColorSelected)
        if (taskColor == TaskColor.DEFAULT) {
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = description,
                onValueChange = { onDescriptionChanged(it) },
                label = { Text(text = stringResource(id = R.string.description)) },
                textStyle = MaterialTheme.typography.bodyLarge,


                )
        } else {

        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = taskColor.color , unfocusedBorderColor =
            taskColor.color.copy(alpha = DISABLE_ALPHA) , focusedLabelColor = taskColor.color , unfocusedLabelColor =
            taskColor.color.copy(alpha = DISABLE_ALPHA)),
            onValueChange = { onDescriptionChanged(it) },
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.bodyLarge,


            )
        }
    }
}

@Composable
@Preview
fun PreviewTaskContent() {
    TaskContent(
        "", {}, "", {}, Priority.HIGH,TaskColor.YELLOW, {}, {}
    )
}