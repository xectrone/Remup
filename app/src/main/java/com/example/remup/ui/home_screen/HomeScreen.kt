package com.example.remup.ui.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.remup.R
import com.example.remup.domain.navigation.Screen
import com.example.remup.ui.home_screen.search_app_bar.NoteListItem
import com.example.remup.ui.home_screen.search_app_bar.SearchAppBar
import com.example.remup.ui.home_screen.search_app_bar.SearchAppBarState
import com.example.remup.ui.home_screen.search_app_bar.SearchAppBarViewModel
import com.example.remup.ui.theme.Constants

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
            if(searchAppBarState == SearchAppBarState.OPENED && !selectionMode)
            {
                SearchAppBar(
                    text = searchTextState,
                    onTextChange = {
                        searchAppBarViewModel.updateSearchTextState(it)
                        viewModel.onSearch(it)
                    },
                    onCloseClicked = {
                        viewModel.onSearchClose()
                        searchAppBarViewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
                                     },
                    onSearchClicked = { },
                )
            }
            else
            {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(top = 42.dp, bottom = 12.dp),
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontWeight =  FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = Constants.Label.MENU)
                        }
                    },
                    actions =
                    {
                        Row()
                        {
                            if (selectionMode && (noteList.any { it.isSelected })) {
                                IconButton(onClick = { viewModel.onDelete() }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = Constants.Label.DELETE)
                                }
                            }
                            IconButton(onClick = { searchAppBarViewModel.updateSearchAppBarState(SearchAppBarState.OPENED) }) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = Constants.Label.SEARCH)
                            }
                        }
                    }
                )
            }
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
                    contentDescription = Constants.Label.ADD,
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