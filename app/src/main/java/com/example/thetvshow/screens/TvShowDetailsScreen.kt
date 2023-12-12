package com.example.thetvshow.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thetvshow.R
import com.example.thetvshow.models.TvShow
import com.example.thetvshow.viewmodel.TvShowDetailsViewModel


@Composable
fun TvShowDetailsScreen() {
    val detailViewModel: TvShowDetailsViewModel = hiltViewModel()
    val trendingShowDetails = detailViewModel.tvShowDetails.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(1f),

        ) {
        TvShowDetailsList(trendingShowDetails = trendingShowDetails.value)
    }

}

@Composable
fun TvShowDetailsList(trendingShowDetails: TvShow?) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
        border = BorderStroke(1.dp, Color(0xFFCCCCCC)),
        content = {
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w342${trendingShowDetails?.posterPath}")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = stringResource(R.string.app_name),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(Dp.Infinity, 350.dp)
                )
                Text(
                    text = trendingShowDetails?.name ?: "",
                    modifier = Modifier.padding(top = 5.dp, start = 1.dp, end = 5.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = trendingShowDetails?.overview ?: "",
                    modifier = Modifier.padding(
                        top = 5.dp,
                        start = 1.dp,
                        end = 5.dp,
                        bottom = 5.dp
                    ), style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    )
}