package com.xectrone.remup.ui.home_screen.search_app_bar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchAppBarViewModel : ViewModel()
{
    private val _searchAppBarState = mutableStateOf(value = SearchAppBarState.CLOSED)
    val searchAppBarState = _searchAppBarState

    private val _searchTextState = mutableStateOf(value = "")
    val searchTextState = _searchTextState

    fun updateSearchAppBarState(newValue: SearchAppBarState)
    {_searchAppBarState.value = newValue}

    fun updateSearchTextState(newValue: String)
    {_searchTextState.value = newValue}

    fun onSearchClosed(){
        _searchTextState.value = ""
        _searchAppBarState.value = SearchAppBarState.CLOSED
    }
}