package com.example.thetvshow.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thetvshow.DataState
import com.example.thetvshow.models.ApiResponse
import com.example.thetvshow.models.TvShow
import com.example.thetvshow.repository.TrendingTvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingTvShowViewModel @Inject constructor( private val repository: TrendingTvRepository): ViewModel() {
    val tvShowList: MutableState<DataState<ApiResponse>?> = mutableStateOf(null)
    val tvSearchShowList: MutableState<DataState<ApiResponse>?> = mutableStateOf(null)

    private val _searchTvString= MutableStateFlow("")
    val searchTvString :StateFlow<String>
        get() = _searchTvString

    private val _isSearching= MutableStateFlow(false)
    val isSearching :StateFlow<Boolean>
        get() = _isSearching

    fun getTvShows() {
        viewModelScope.launch {
            repository.getTrendingTvList().onEach {
                tvShowList.value= it
            }.launchIn(viewModelScope)
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchTvString.value = text
        viewModelScope.launch {
            flowOf(text).debounce(500).onEach { _isSearching.value= true }.distinctUntilChanged().onEmpty {
                tvSearchShowList.value = null
            }.debounce(700).flatMapLatest {
                _isSearching.value =it.isNullOrEmpty().not()
                repository.getTrendingTvSearchList(it)
            }.collect {
                if (it is DataState.Success) {
                    it.data
                }
                tvSearchShowList.value = it

            }
        }
    }
}