package com.example.remup.ui.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.remup.data.view_model.NoteViewModel
import com.example.remup.domain.navigation.Screen
import com.example.remup.ui.home_screen.search_app_bar.NoteListItem
import com.example.remup.ui.home_screen.search_app_bar.SearchAppBar
import com.example.remup.ui.home_screen.search_app_bar.SearchAppBarViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    searchAppBarViewModel: SearchAppBarViewModel =  hiltViewModel(),
    navController: NavHostController
){

    viewModel.getFlashcardList()
    val noteList by viewModel.noteList.collectAsStateWithLifecycle()
    val selectionMode by viewModel.selectionMode

    val searchAppBarState by searchAppBarViewModel.searchAppBarState
    val searchTextState by searchAppBarViewModel.searchTextState

    Scaffold(
        //region - TopBar -
        topBar = {
            SearchAppBar(
                text = searchTextState,
                onTextChange = {
                    searchAppBarViewModel.updateSearchTextState(it)
                    viewModel.onSearch(it) },
                onCloseClicked = { viewModel.onSearchClose() },
                onSearchClicked = { },
                onSideButtonClick = { navController.navigate(Screen.ShowNote.route)}
            )
        },
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
            items(noteList){
                NoteListItem(
                    item = it,
                    onClick = {
                        if (selectionMode)
                            viewModel.onItemClick(it)
                        else {
//                            searchAppBarViewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
                            navController.navigate(route = Screen.AddEditNote.navArg(it.note.id))
                        }
                    },
                    onLongClick = { viewModel.onItemLongClick(it) }
                )

            }
        }
    }



}