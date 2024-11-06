package com.randomthings.presentation.jokes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.randomthings.R
import com.randomthings.presentation.theme.AppTheme
import com.randomthings.presentation.theme.black
import com.randomthings.presentation.theme.white


@Composable
fun JokeItem(
    modifier: Modifier = Modifier,
    text: String,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.black)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_quotation),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.white,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .size(32.dp)
                .scale(1f, 1f)
                .alpha(0.7f)
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.white,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .alpha(0.9f),
            maxLines = 4,
            style = MaterialTheme.typography.headlineLarge,
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_quotation),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.white,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .size(32.dp)
                .scale(1f, -1f)
                .alpha(0.7f)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun JokeItemPreview() {
    AppTheme {
        JokeItem(
            text = "Boom bang badum boop.",
        )
    }
}

