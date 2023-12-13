package com.example.thetvshow.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thetvshow.DataState
import com.example.thetvshow.R
import com.example.thetvshow.models.ApiResponse
import com.example.thetvshow.models.TvShow
import com.example.thetvshow.viewmodel.TrendingTvShowViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingTvShowScreen(onClick: (category: String) -> Unit) {
    val trendingTvShowViewModel: TrendingTvShowViewModel = hiltViewModel()
    val searchTvText: State<String> = trendingTvShowViewModel.searchTvString.collectAsState()
    val isSearching: State<Boolean> = trendingTvShowViewModel.isSearching.collectAsState()
    val trendingShows = remember { mutableStateOf(arrayListOf<TvShow>()) }
    val trendingSearchShow = remember { mutableStateOf(arrayListOf<TvShow>()) }

    LaunchedEffect(key1 = 0) {
        trendingTvShowViewModel.getTvShows()
    }


    if (trendingTvShowViewModel.tvShowList.value is DataState.Success<ApiResponse>) {
        trendingShows.value =
            (trendingTvShowViewModel.tvShowList.value as DataState.Success<ApiResponse>).data.results as ArrayList
    }

    if (trendingTvShowViewModel.tvSearchShowList.value is DataState.Success<ApiResponse>) {
        trendingSearchShow.value =
            (trendingTvShowViewModel.tvSearchShowList.value as DataState.Success<ApiResponse>).data.results as ArrayList
    }


    if (trendingShows.value.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...", style = MaterialTheme.typography.headlineLarge)
        }
    } else {
        Column {
            TextField(
                value = searchTvText.value,
                onValueChange = trendingTvShowViewModel::onSearchTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                placeholder = {
                    Text(
                        text = "Search tv shows"
                    )
                })
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                items(trendingShows.value.distinct()) {
                    TrendingTvShowItem(trendingShow = it, onClick)
                }
            }
        }

    }


}

@Composable
fun TrendingTvShowItem(trendingShow: TvShow, onClick: (category: String) -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                onClick(trendingShow.id.toString())
            }
            .size(300.dp)
            .clip(RoundedCornerShape(8.dp))
            .paint(
                painter = painterResource(id = R.drawable.bg),
                contentScale = ContentScale.Crop
            )
            .border(1.dp, Color(0xFFEEEEEE)),

        ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w342${trendingShow.posterPath}")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(Dp.Infinity, 240.dp)
            )

            Text(
                text = trendingShow.name,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(0.dp, 20.dp),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}