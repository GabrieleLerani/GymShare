package com.project.gains.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.gains.data.Categories
import com.project.gains.theme.GainsAppTheme

@Composable
fun SearchAppBar(
    text: String,
    placeholder: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    // set enabled to false if you want to have onClick function triggered when the user clicks on the search bar
    // otherwise set it to false (it should be the default behavior)
    onClick: () -> Unit,
    enabled: Boolean
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(15.dp),
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
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp)
    )
}



@Composable
fun ResearchFilter(
    categories: List<Categories>,
    selectedCategory: Categories?,
    onCategorySelected: (Categories) -> Unit
) {
    Column(Modifier.padding(15.dp)) {
        Text(text = "Filter by Categories", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(10.dp))

        categories.forEach { category ->
            val isSelected = selectedCategory == category
            FilterChip(
                category = category,
                isSelected = isSelected,
                onCategorySelected = onCategorySelected
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun FilterChip(
    category: Categories,
    isSelected: Boolean,
    onCategorySelected: (Categories) -> Unit
) {

            androidx.compose.material3.Text(
                text = category.toString(),
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ), // Make it bigger and bold
                color = if (isSelected) androidx.compose.material3.MaterialTheme.colorScheme.primary else MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .clickable { onCategorySelected(category) }
                    .background(
                        androidx.compose.material3.MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(16.dp)
                    )
                    .border(
                        border = BorderStroke(
                            width = 3.dp,
                            color = if (isSelected) androidx.compose.material3.MaterialTheme.colorScheme.primary else androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        ), shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth()
                    .padding(16.dp) // Add padding for better spacing
                    .padding(16.dp) // Inner padding for the text itself
            )
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