package com.randomthings.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.randomthings.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier,
              query: String,
              placeHolderString: (String) = stringResource(id = R.string.search),
              onQueryChange: (String) -> Unit,
              onSearch: (String) -> Unit,
              expanded: Boolean,
              onExpandedChange: (Boolean) -> Unit,
              content: @Composable ColumnScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background,
                CircleShape
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DockedSearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch,
                    expanded = expanded,
                    onExpandedChange = onExpandedChange,
                    placeholder = { Text(text = placeHolderString) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search),
                        )
                    },
                )
            },
            modifier = modifier
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = onExpandedChange,
            content = {
                content
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun SearchbarPreview() {
    SearchBar(
        modifier = Modifier,
        query = "",
        onQueryChange = { },
        onSearch = { },
        expanded = false,
        onExpandedChange = { },
        content = {}
    )
}
