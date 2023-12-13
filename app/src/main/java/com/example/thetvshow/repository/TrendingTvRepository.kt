package com.example.thetvshow.repository

import com.example.thetvshow.DataState
import com.example.thetvshow.api.TvApi
import com.example.thetvshow.models.ApiResponse
import com.example.thetvshow.models.TvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrendingTvRepository @Inject constructor( private val tvApi: TvApi){

    private  val _trendingTv= MutableStateFlow<List<TvShow>>(emptyList())
    val trendingTv: StateFlow<List<TvShow>>
        get() = _trendingTv

    private  val _trendingTvSearch= MutableStateFlow<List<TvShow>>(emptyList())
    val trendingTvSearch: StateFlow<List<TvShow>>
        get() = _trendingTvSearch


    private  val _tvShowDetails= MutableStateFlow<TvShow?>(null)
    val tvShowDetails: StateFlow<TvShow?>
        get() = _tvShowDetails


    suspend fun getTrendingTvList() : Flow<DataState<ApiResponse>> = flow  {
        emit(DataState.Loading)
        val response=  tvApi.getTrendingTvShows()
        try {
            if (response.isSuccessful && response.body() !=null){
                emit(DataState.Success(response.body()!!))
            }
        }catch (e: Exception){
            emit(DataState.Error(e))
        }

    }

    suspend fun getTrendingTvSearchList(query: String): Flow<DataState<ApiResponse>>  = flow {
        emit(DataState.Loading)
        val response=  tvApi.searchTvShow(query)
        try {
            if (response.isSuccessful && response.body() !=null){
                emit(DataState.Success(response.body()!!))
            }
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun getTvShowDetails(id: String){
        val response=  tvApi.getTvShow(id)
        if (response.isSuccessful && response.body() !=null){
                response?.body()?.let { _tvShowDetails.emit(it) }
        }
    }
}