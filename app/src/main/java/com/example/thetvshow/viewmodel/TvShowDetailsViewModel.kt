package com.example.thetvshow.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thetvshow.models.TvShow
import com.example.thetvshow.repository.TrendingTvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(private val repository: TrendingTvRepository, private val savedStateHandle: SavedStateHandle): ViewModel()  {

    val tvShowDetails: StateFlow<TvShow?>
        get() = repository.tvShowDetails

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>("show") ?:""
            repository.getTvShowDetails(id = id)
        }
    }
}