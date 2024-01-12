package com.example.remup.ui.add_edit_note_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.remup.R
import com.example.remup.data.model.Note
import com.example.remup.data.view_model.NoteViewModel

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    id: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    val data by viewModel.data

    Column(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.status_bar_top_padding))
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            //region - Back Button -
            IconButton(
                onClick =
                {
                    viewModel.onBackClick(id)
                    navController.navigateUp()
                })
            {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = MaterialTheme.colors.primary)
            }

//            Spacer(modifier = Modifier.weight(1f))
            //endregion

            //region - Edit Button -
//            IconButton(
//
//                onClick = {
//                    if(editMode)
//                    {
//                        editMode = false
//                        keyboard?.hide()
//                        if(data != "") {
//                            navNote = navNote.updateNote(newData = data)
//                            if (isNewNote) {
//                                noteViewModel.addNote(navNote)
//                                isNewNote = false
//                            } else
//                                noteViewModel.updateNote(navNote)
//
//                        }
//                    }
//                    else
//                        editMode = true
//                })
//            {
//                Icon(imageVector = if(editMode) Icons.Default.Check else Icons.Default.Edit, contentDescription = null, tint = MaterialTheme.colors.primary)
//            }
//            endregion

            //region - Close Button -
            IconButton(onClick = { navController.navigateUp() })
            {
                Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = MaterialTheme.colors.primary)
            }
            //endregion

        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 7.dp),
            value = data,
            onValueChange = { viewModel.onDataChange(it) },
            placeholder = {
                Text(
                    text = "Type here...",
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primaryVariant)},
            textStyle = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colors.primary,),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.primaryVariant,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )


    }


    //region - Back Press Handler -
    BackHandler() {
        viewModel.onBackClick(id)
        navController.navigateUp()
    }
    //endregion



}