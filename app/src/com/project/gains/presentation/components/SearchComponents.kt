package com.project.gains.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.gains.data.Categories
import com.project.gains.presentation.components.events.ManageCategoriesEvent
import com.project.gains.presentation.explore.events.SearchEvent

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
    searchViewModel: SearchViewModel,
    searchGymPostHandler: (SearchEvent.SearchGymPostEvent) -> Unit,
    resetGymPostHandler: (SearchEvent.ResetPostEvent) -> Unit,
    assignCategoryHandler: (ManageCategoriesEvent.AssignCategoryEvent) -> Unit

    ){
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    val items = remember {
        mutableListOf("gabriele","gianmarco","carlo","curl", "planck")
    }

    val categories = searchViewModel.categories
    val selectedCategory by searchViewModel.selectedCategory.observeAsState()
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            searchGymPostHandler(SearchEvent.SearchGymPostEvent(text = text, selectedCategory = selectedCategory))
            items.add(text)
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
                assignCategoryHandler(ManageCategoriesEvent.AssignCategoryEvent(category))
            })

        items.forEach {
            Row(modifier = Modifier.padding(14.dp)){
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
    categories: List<Categories>,
    selectedCategory: Categories?,
    onCategorySelected: (Categories) -> Unit
) {
    Column(Modifier.padding(15.dp)) {
        Text(text = "Filter by Categories", style = MaterialTheme.typography.headlineSmall)

        //Spacer(modifier = Modifier.height(10.dp))

        FlowRow (
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            categories.forEach { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    category = category,
                    isSelected = isSelected,
                    onCategorySelected = onCategorySelected,

                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun FilterChip(
    category: Categories,
    isSelected: Boolean,
    onCategorySelected: (Categories) -> Unit
) {
    androidx.compose.material3.FilterChip(
        onClick = { onCategorySelected(category) },
        label = {
            Text(
                text = category.toString(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                ,
            )
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


@OptIn(ExperimentalMaterialApi::class)
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
fun SearchAppBarPreview() {
    Scaffold {
        val searchViewModel : SearchViewModel = hiltViewModel()
        /*SearchAppBar(
            value = "",
            placeholder = "Some random text",
            onValueChange = {},
            onCloseClicked = {},
            onSearchClicked = {},
            onClick = {},
            enabled = true
        )*/
        FeedSearchBar(searchViewModel = searchViewModel, searchGymPostHandler = {}, resetGymPostHandler = {}) {
            
        }
    }

}

enum class SearchWidgetState {
    OPENED,
    CLOSED
}