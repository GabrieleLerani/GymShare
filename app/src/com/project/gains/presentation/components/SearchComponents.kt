package com.project.gains.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.gains.data.Categories

@Composable
fun SearchAppBar(
    text: String,
    placeholder: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    // set enabled to false if you want to have onClick function triggered when the user clicks on the search bar
    // otherwise set it to false (it should be the default behaviour)
    onClick: () -> Unit,
    enabled: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary,
    ) {
        TextField(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth(),
            enabled = enabled,
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = placeholder,
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            )
        )
    }
}

@Composable
fun ResearchFilter(
    categories: List<Categories>,
    selectedCategory: Categories?,
    onCategorySelected: (Categories) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Filter by Categories", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(8.dp))

        categories.forEach { category ->
            val isSelected = selectedCategory == category
            FilterChip(
                category = category,
                isSelected = isSelected,
                onCategorySelected = onCategorySelected
            )
        }
    }
}

@Composable
fun FilterChip(
    category: Categories,
    isSelected: Boolean,
    onCategorySelected: (Categories) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onCategorySelected(category) },
        shape = MaterialTheme.shapes.small,
        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        contentColor = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        elevation = 4.dp
    ) {
        Text(
            text = category.toString(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResearchFilterPreview() {
    var selectedCategory by remember { mutableStateOf(Categories.User) }

    ResearchFilter(
        categories = listOf(Categories.User, Categories.Workout, Categories.Keyword, Categories.Social),
        selectedCategory = selectedCategory,
        onCategorySelected = { category ->
            selectedCategory = category
        }
    )
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        placeholder = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {},
        onClick = {},
        enabled = true
    )
}

enum class SearchWidgetState {
    OPENED,
    CLOSED
}