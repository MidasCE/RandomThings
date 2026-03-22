package com.randomthings.presentation.jokes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.randomthings.presentation.theme.AppTheme
import com.randomthings.presentation.theme.JokeBubbleBlue
import com.randomthings.presentation.theme.JokeBubbleGreen
import com.randomthings.presentation.theme.JokeBubbleOrange
import com.randomthings.presentation.theme.JokeBubblePurple

@Composable
fun JokeItem(
    modifier: Modifier = Modifier,
    text: String,
    index: Int = 0,
) {
    val isLeft = index % 2 == 0
    val bubbleColor = when (index % 4) {
        0 -> JokeBubbleBlue
        1 -> JokeBubbleOrange
        2 -> JokeBubbleGreen
        else -> JokeBubblePurple
    }
    val bubbleShape = if (isLeft) {
        RoundedCornerShape(topStart = 4.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
    } else {
        RoundedCornerShape(topStart = 20.dp, topEnd = 4.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
    ) {
        if (isLeft) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(bubbleColor, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEmotions,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .weight(1f, fill = false)
                .background(color = bubbleColor, shape = bubbleShape)
                .padding(horizontal = 14.dp, vertical = 10.dp),
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        if (!isLeft) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(bubbleColor, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEmotions,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF87CEEB)
@Composable
private fun JokeItemLeftPreview() {
    AppTheme {
        JokeItem(text = "Why don't scientists trust atoms? Because they make up everything!", index = 0)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF87CEEB)
@Composable
private fun JokeItemRightPreview() {
    AppTheme {
        JokeItem(text = "What do you call a fake noodle? An Impasta!", index = 1)
    }
}
