package com.randomthings.presentation.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.randomthings.R
import com.randomthings.presentation.theme.AppTheme


@Composable
fun TopBarTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column {
        Surface(
            modifier = modifier.height(80.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(56.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun TopBarTitlePreview() {
    AppTheme {
        TopBarTitle(
            title = stringResource(R.string.app_name),
        )
    }
}
