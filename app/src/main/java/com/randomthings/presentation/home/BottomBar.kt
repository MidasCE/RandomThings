package com.randomthings.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import com.randomthings.R

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.bottom_navigation_home)
                )
            },
            selected = true,
            onClick = {}
        )
    }
}