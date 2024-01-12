package com.example.remup.ui.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.remup.data.view_model.NoteViewModel
import com.example.remup.domain.navigation.Screen
import com.example.remup.ui.home_screen.search_app_bar.SearchAppBar
import com.example.remup.ui.home_screen.search_app_bar.SearchAppBarViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel = hiltViewModel(),
    searchAppBarViewModel: SearchAppBarViewModel =  hiltViewModel(),
    navController: NavHostController
){

    val originalNoteList = noteViewModel.noteList().collectAsState(initial = emptyList()).value
    var noteList by remember { mutableStateOf(originalNoteList) }
    noteList = originalNoteList


    var selectionMode by remember { mutableStateOf(false) }
    val searchAppBarState by searchAppBarViewModel.searchAppBarState
    val searchTextState by searchAppBarViewModel.searchTextState

    Scaffold(
        //region - TopBar -
        topBar = {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { it ->
                    searchAppBarViewModel.updateSearchTextState(it)
                    noteList =
                        originalNoteList.filter { it.doesMatchSearchQuery(searchTextState) } },
                onCloseClicked = { noteList = originalNoteList },
                onSearchClicked = { },
                onSideButtonClick = { navController.navigate(Screen.ShowNote.route)}
            )
        },
        //endregion


        //endregion

        //region - Floating Action Button -
        floatingActionButton =
        {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = Screen.AddEditNote.navArg())
                },

                backgroundColor = MaterialTheme.colors.primaryVariant
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colors.surface
                )
            }
        }
        //endregion
    ){
        LazyVerticalGrid(
            modifier = modifier
                .padding(horizontal = 6.5.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.Start
        ){
            items(noteList.size){
                Box(
                    modifier = modifier
                        .padding(4.5.dp)
                        .height(150.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(MaterialTheme.colors.surface)
                        .clickable { navController.navigate(Screen.AddEditNote.navArg(noteList[it].id)) },
                    contentAlignment = Alignment.TopStart)
                {
                    Column(
                        modifier = modifier
                            .padding(25.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween

                    )
                    {
                        Text(
                            modifier = modifier
                                .padding(bottom = 15.dp),
                            text = noteList[it].data ,
                            color = MaterialTheme.colors.primary,
                            maxLines = 4,
                            textAlign = TextAlign.Justify,
                        )

                        Text(
                            fontSize = 13.sp,
                            text = noteList[it].created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)),
                            color = MaterialTheme.colors.primaryVariant,
                            maxLines = 1,
                            textAlign = TextAlign.Start
                        )
                    }

                }
            }
        }
    }



}