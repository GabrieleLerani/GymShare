package com.project.gains.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.gains.data.Categories

@Composable
fun SearchAppBar(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    // set enabled to false if you want to have onClick function triggered when the user clicks on the search bar
    // otherwise set it to false (it should be the default behavior)
    onClick: () -> Unit,
    enabled: Boolean
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(15.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (isFocused) {
                    keyboardController?.show()
                }
            },
        interactionSource = interactionSource,
        enabled = enabled,
        value = value,
        onValueChange = {
            onValueChange(it)
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
                    if (value.isNotEmpty()) {
                        onValueChange("")
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
                onSearchClicked(value)
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.onTertiary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp)
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResearchFilter(
    categories: List<Categories>,
    selectedCategory: Categories?,
    onCategorySelected: (Categories) -> Unit
) {
    Column(Modifier.padding(15.dp)) {
        Text(text = "Filter by Categories", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow (
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            modifier = Modifier
                .fillMaxWidth()
        ) {
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
}

@Composable
fun FilterChip(
    category: Categories,
    isSelected: Boolean,
    onCategorySelected: (Categories) -> Unit
) {
    androidx.compose.material3.Text(
        text = category.toString(),
        style = androidx.compose.material3.MaterialTheme.typography.headlineSmall.copy(),
        color = if (isSelected) androidx.compose.material3.MaterialTheme.colorScheme.error else MaterialTheme.colors.primary,
        modifier = Modifier
            .clickable { onCategorySelected(category) }
            .background(
                color = if (isSelected) androidx.compose.material3.MaterialTheme.colorScheme.errorContainer.copy(
                    alpha = 0.1f
                ) else MaterialTheme.colors.primary.copy(alpha = 0.1f),
                RoundedCornerShape(30.dp)
            )
            .padding(10.dp) // Add padding for better spacing
            .padding(3.dp) // Inner padding for the text itself
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
        value = "",
        placeholder = "Some random text",
        onValueChange = {},
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