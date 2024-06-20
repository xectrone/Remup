package com.example.remup.ui.add_edit_note_screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.remup.ui.theme.Constants
import com.example.remup.ui.theme.CustomTypography
import com.example.remup.ui.theme.Dimen
import com.example.remup.ui.theme.LocalCustomColorPalette
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    id: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    val data by viewModel.data
    val activity = LocalContext.current as? Activity
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (id==-1){
            delay(200)
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        //region - Floating Action Button -
        floatingActionButton =
        {
            FloatingActionButton(
                modifier = Modifier.imePadding(),
                backgroundColor = LocalCustomColorPalette.current.accent,
                onClick =
                {
                    viewModel.onBackClick(id)
                    if (!navController.navigateUp())
                        activity?.finish()
                }
            )
            {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = Constants.Label.ADD,
                    tint = LocalCustomColorPalette.current.background

                )
            }
        },
        //endregion

        backgroundColor = LocalCustomColorPalette.current.background
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = Dimen.Padding.statusBar),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                //region - Back Button -
                IconButton(
                    onClick =
                    {
                        viewModel.onBackClick(id)
                        if (!navController.navigateUp())
                            activity?.finish()
                    })
                {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = Constants.Label.BACK,
                        tint = LocalCustomColorPalette.current.primary
                    )
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
//                                Icon(imageVector = if(editMode) Icons.Default.Check else Icons.Default.Edit, contentDescription = Constants.Label.EDIT, tint = LocalCustomColorPalette.current.primary)
                //            }
                //            endregion

                //region - Close Button -
                IconButton(
                    onClick = {
                        viewModel.onDeleteClick(id)
                        if (!navController.navigateUp())
                            activity?.finish()
                    }
                )
                {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = Constants.Label.DELETE,
                        tint = LocalCustomColorPalette.current.primary
                    )
                }
                //endregion

            }

            TextField(
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .padding(horizontal = 7.dp),
                value = data,
                onValueChange = { viewModel.onDataChange(it) },
                textStyle = CustomTypography.body,
                placeholder ={
                    Text(
                        text = "Type here...",
                        style = CustomTypography.body.copy(fontStyle = FontStyle.Italic),
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = LocalCustomColorPalette.current.secondary,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )


        }
    }


    //region - Back Press Handler -
    BackHandler() {
        viewModel.onBackClick(id)
        if (!navController.navigateUp())
            activity?.finish()
    }
    //endregion



}