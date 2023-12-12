package com.example.thetvshow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thetvshow.models.TvShow
import com.example.thetvshow.repository.TrendingTvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingTvShowViewModel @Inject constructor( private val repository: TrendingTvRepository): ViewModel() {
    val tvShowList: StateFlow<List<TvShow>>
        get() = repository.trendingTv

    private val _searchTvString= MutableStateFlow("")
    val searchTvString :StateFlow<String>
        get() = _searchTvString

    private val _isSearching= MutableStateFlow(false)
    val isSearching :StateFlow<Boolean>
        get() = _isSearching

    init {
        viewModelScope.launch {
            repository.getTrendingTvList()
        }
    }

    fun onSearchTextChanged(text:String){
        _searchTvString.value=text
    }
}