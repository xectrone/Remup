package com.xectrone.remup.ui.home_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.xectrone.remup.ui.theme.LocalCustomColorPalette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    searchAppBarViewModel: SearchAppBarViewModel =  hiltViewModel(),
    navController: NavHostController
){
    val noteList by viewModel.noteList.collectAsStateWithLifecycle()
    val selectionMode by viewModel.selectionMode

    val searchAppBarState by searchAppBarViewModel.searchAppBarState
    val searchTextState by searchAppBarViewModel.searchTextState

    val isExpanded by viewModel.isExpanded

    val context = LocalContext.current
    var backPressHandled by remember { mutableStateOf(true) }



    LaunchedEffect(key1 = navController.currentBackStackEntry) {
        viewModel.observeSortOption()
        viewModel.getFlashcardList()
    }

    LaunchedEffect(key1 = backPressHandled) {
        delay(2000)
        backPressHandled = true
    }

    fun allClear(){
        viewModel.onClear()
        viewModel.onSerchClose()
        searchAppBarViewModel.onSearchClosed()
    }

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
//                    navigationIcon = {
//                        IconButton(onClick = {}) {
//                            Icon(imageVector = Icons.Default.Menu,
//                                contentDescription = Constants.Labels.MENU,
//                                tint = LocalCustomColorPalette.current.primary
//                            )
//                        }
//
//                    },
                    actions =
                    {
                        Row()
                        {
                            if (selectionMode && (noteList.any { it.isSelected })) {
                                IconButton(onClick = {
                                    viewModel.onDelete()
                                    allClear()
                                }) {
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

                            IconButton(onClick = {
                                viewModel.showMenu()
                                allClear()
                            })
                            {
                                Icon(painter = painterResource(id = R.drawable.round_sort_24), contentDescription = Constants.Labels.CLEAR, tint = LocalCustomColorPalette.current.primary)
                                DropdownMenu(expanded = isExpanded, onDismissRequest = { viewModel.hideMenu() })
                                {
                                    DropdownMenuItem(onClick = { viewModel.onSort(SortOptions.contentASC) })
                                    { Text(text = Constants.Labels.SortOptions.contentASC) }

                                    DropdownMenuItem(onClick = { viewModel.onSort(SortOptions.contentDESC) })
                                    { Text(text = Constants.Labels.SortOptions.cotnentDESC) }

                                    DropdownMenuItem(onClick = { viewModel.onSort(SortOptions.lastModifiedASC) })
                                    { Text(text = Constants.Labels.SortOptions.lastModifiedASC) }

                                    DropdownMenuItem(onClick = { viewModel.onSort(SortOptions.lastModifiedDESC) })
                                    { Text(text = Constants.Labels.SortOptions.lastModifiedDESC) }
                                }

                            }

                            IconButton(
                                onClick =
                                {
                                    navController.navigate(Screen.Setting.route)
                                    allClear()
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
            if(searchAppBarState == SearchAppBarState.OPENED && !selectionMode) { }
            else{
                FloatingActionButton(
                    onClick = {
                        navController.navigate(route = Screen.AddEditNote.navArg())
                        allClear()
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
            items(noteList, key = {it.note.id}){
                NoteListItem(
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(durationMillis = 500)
                    ),
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
    BackHandler(enabled = backPressHandled) {
        if (searchAppBarState == SearchAppBarState.OPENED) {
            if (searchTextState.isNotBlank())
                searchAppBarViewModel.updateSearchTextState("")
            else {
                viewModel.onSerchClose()
                searchAppBarViewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
            }

        }
        else
            if(selectionMode)
                allClear()
            else {
                Toast.makeText(context, Constants.Toast.DOUBLE_BACK, Toast.LENGTH_SHORT).show()
                backPressHandled = false
            }
    }
    //endregion


}