package com.xectrone.remup.ui.home_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.xectrone.remup.R
import com.xectrone.remup.domain.navigation.Screen
import com.xectrone.remup.ui.home_screen.search_app_bar.NoteListItem
import com.xectrone.remup.ui.home_screen.search_app_bar.SearchAppBar
import com.xectrone.remup.ui.home_screen.search_app_bar.SearchAppBarState
import com.xectrone.remup.ui.home_screen.search_app_bar.SearchAppBarViewModel
import com.xectrone.remup.ui.theme.Constants
import com.xectrone.remup.ui.theme.CustomTypography
import com.xectrone.remup.ui.theme.Dimen
import com.xectrone.remup.ui.theme.LocalCustomColorPalette

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
                    backgroundColor = LocalCustomColorPalette.current.background,
                    contentColor = LocalCustomColorPalette.current.primary,
                    modifier = Modifier.padding(top = 42.dp, bottom = 12.dp),
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = CustomTypography.h2,
                            color = LocalCustomColorPalette.current.primary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Menu,
                                contentDescription = Constants.Labels.MENU,
                                tint = LocalCustomColorPalette.current.primary
                            )
                        }

                    },
                    actions =
                    {
                        Row()
                        {
                            if (selectionMode && (noteList.any { it.isSelected })) {
                                IconButton(onClick = { viewModel.onDelete() }) {
                                    Icon(imageVector = Icons.Default.Delete,
                                        contentDescription = Constants.Labels.DELETE,
                                        tint = LocalCustomColorPalette.current.primary
                                        )
                                }
                            }
                            IconButton(onClick = { searchAppBarViewModel.updateSearchAppBarState(SearchAppBarState.OPENED) }) {
                                Icon(imageVector = Icons.Default.Search,
                                    contentDescription = Constants.Labels.SEARCH,
                                    tint = LocalCustomColorPalette.current.primary
                                )
                            }
                            IconButton(
                                onClick =
                                {
                                    navController.navigate(Screen.Setting.route)
                                }
                            )
                            { Icon(imageVector = Icons.Rounded.Settings,
                                contentDescription = Constants.Labels.SETTINGS,
                                tint = LocalCustomColorPalette.current.primary
                            ) }


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

                backgroundColor = LocalCustomColorPalette.current.accent
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.Labels.ADD,
                    tint = LocalCustomColorPalette.current.surface
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
                            searchAppBarViewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
                            navController.navigate(route = Screen.AddEditNote.navArg(it.note.id))
                        }
                    },
                    onLongClick = { viewModel.onItemLongClick(it) }
                )

            }
        }
    }

    //region - Back Press Handler -
    BackHandler() {
        if (searchAppBarState == SearchAppBarState.OPENED) {
            if (searchTextState.isNotBlank())
                searchAppBarViewModel.updateSearchTextState("")
            else {
                viewModel.onSerchClose()
                searchAppBarViewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
            }
        }
        else
            navController.popBackStack()
    }
    //endregion

}