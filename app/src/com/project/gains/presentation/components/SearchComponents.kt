package com.project.gains.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import com.project.gains.presentation.explore.events.SearchEvent
import com.project.gains.util.getExerciseCategory
import com.project.gains.util.getFeedCategory

@OptIn(ExperimentalMaterial3Api::class)
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

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(10.dp)
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
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.titleSmall.fontSize
        ),
        singleLine = true,
        leadingIcon = {
            IconButton(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (value.isNotEmpty()) {
                        onValueChange("")
                    } else {
                        keyboardController?.hide() // Hide keyboard when searching
                        onCloseClicked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = MaterialTheme.colorScheme.onSurface
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
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(16.dp)
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FeedSearchBar(
    modifier : Modifier,
    categories : List<String>,
    selectedCategory: String?,
    searchGymPostHandler: (SearchEvent.SearchGymPostEvent) -> Unit,
    resetGymPostHandler: (SearchEvent.ResetPostEvent) -> Unit,
    assignCategoryHandler: (ManageCategoriesEvent.AssignCategoryEvent) -> Unit){


    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val items = remember {
        mutableListOf("curl", "planck") // dummy values
    }


    SearchBar(

        modifier = modifier,
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            searchGymPostHandler(SearchEvent.SearchGymPostEvent(text = text, selectedCategory =  selectedCategory?.let { it1 ->
                getFeedCategory(
                    it1
                )
            }))

            // add only if not blank
            if (text.isNotBlank() && !items.contains(text)) {
                items.add(text)
            }
            active = false

        },
        active = active,
        onActiveChange = { active = it},
        placeholder = { Text("Search")},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()){
                            text = ""
                            resetGymPostHandler(SearchEvent.ResetPostEvent)
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                )
            }

        }
    ){
        ResearchFilter(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                assignCategoryHandler(ManageCategoriesEvent.AssignCategoryEvent(getFeedCategory(category) ))
            })

        items.forEach {
            Row(modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
                .clickable {
                    text = it
                }){
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Default.History,
                    contentDescription = "History Icon",
                    )
                Text(text = it)
            }
        }
    }

}

@ExperimentalMaterialApi
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResearchFilter(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit
) {

    val scrollState = rememberScrollState()

    FlowRow (
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState).padding(8.dp)
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

@ExperimentalMaterialApi
@Composable
fun FilterChip(
    category: String,
    isSelected: Boolean,
    onCategorySelected: (String) -> Unit
) {
    androidx.compose.material3.FilterChip(
        onClick = {
            if (isSelected) {
                onCategorySelected("")
            } else
                onCategorySelected(category)
                  },
        label = {
            Text(text = category)
        },
        selected = isSelected,

        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ExerciseSearchBar(
    modifier : Modifier,
    categories : List<String>,
    selectedCategory: String?,
    onSearchClicked: (String) -> Unit,
    assignCategoryHandler: (ManageCategoriesEvent.AssignExerciseCategoryEvent) -> Unit)
{
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val items = remember {
        mutableListOf("Plank","Bench press")
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar(
            modifier = modifier,
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                onSearchClicked(text)
                if (text.isNotBlank() && !items.contains(text)) {
                    items.add(text)
                }
                active = false
            },
            active = active,
            onActiveChange = { active = it},
            placeholder = { Text("Search")},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()){
                                text = ""

                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                    )
                }

            }
        ) {

            items.forEach {
                Row(modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth()
                    .clickable {
                        text = it
                    }){
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = "History Icon",
                    )
                    Text(text = it)
                }
            }
        }

        ResearchFilter(
            categories = categories,
            selectedCategory = selectedCategory.toString(),
            onCategorySelected = { category ->
                assignCategoryHandler(ManageCategoriesEvent.AssignExerciseCategoryEvent(
                    getExerciseCategory(category)
                ))
                onSearchClicked(text)
            }
        )

    }
}
