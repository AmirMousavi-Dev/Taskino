package ir.codroid.taskino.ui.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ir.codroid.taskino.R
import ir.codroid.taskino.ui.theme.EMPTY_LIST_IMAGE_SIZE
import ir.codroid.taskino.ui.theme.MediumGray
import ir.codroid.taskino.ui.theme.listItemBackgroundColor

@Composable
fun EmptyList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.listItemBackgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(EMPTY_LIST_IMAGE_SIZE),
            painter = painterResource(id = R.drawable.ic_empty_list),
            contentDescription = stringResource(id = R.string.no_tasks_found)
        )

        Text(
            text = stringResource(id = R.string.no_tasks_found),
            style = MaterialTheme.typography.titleLarge,
            color = MediumGray,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
fun PreviewEmptyList() {
    EmptyList()
}