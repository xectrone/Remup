package com.example.remup.ui.home_screen.search_app_bar


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remup.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSideButtonClick:() -> Unit,
)
{
    val keyboard = LocalSoftwareKeyboardController.current
    var isSearching by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.status_bar_top_padding))
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = dimensionResource(id = R.dimen.top_bar_elevation),
        color = MaterialTheme.colors.background
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            //region - Text Field -
            TextField(
                modifier = Modifier
                    .scale(0.87f)
                    .padding(vertical = 12.dp)
                    .wrapContentHeight()
                    .weight(1f),
                value = text,
                shape = RoundedCornerShape(27.dp),
                onValueChange = {
                    onTextChange(it)
                    if (it != "")
                        isSearching = true
                },
                placeholder = {
                    Text(
                        text = "Search here...",
                        fontSize = 15.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )
                },
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    color = MaterialTheme.colors.primary
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                },
                trailingIcon = {
                    if (isSearching) {
                        IconButton(onClick = {
                            if (text.isNotEmpty())
                                onTextChange("")
                            else {
                                keyboard?.hide()
                                isSearching = false
                                onCloseClicked()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                    } else {
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) }),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    cursorColor = if (isSearching) MaterialTheme.colors.primaryVariant else Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
            //endregion


            if(!isSearching) {
                Button(modifier = Modifier
                    .scale(0.87f)
                    .padding(end = 21.dp, top = 12.dp, bottom = 12.dp)
                    .width(57.dp)
                    .height(57.dp),
                    shape = CircleShape,
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    onClick = {onSideButtonClick()}
                ) {}
            }
        }
    }
}

