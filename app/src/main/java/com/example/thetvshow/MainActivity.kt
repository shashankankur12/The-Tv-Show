package com.example.thetvshow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thetvshow.api.TvApi
import com.example.thetvshow.screens.TrendingTvShowScreen
import com.example.thetvshow.screens.TvShowDetailsScreen
import com.example.thetvshow.ui.theme.TheTvShowTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var tvApi: TvApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TheTvShowTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Trending Tv Shows")
                            }, colors = TopAppBarDefaults.largeTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            )
                        )
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        App()
                    }
                }

            }
        }
    }
}


@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "show") {
        composable(route = "show") {
            TrendingTvShowScreen {
                navController.navigate("detail/${it}")
            }
        }
        composable(route = "detail/{show}",
            arguments = listOf(
                navArgument("show") {
                    type = NavType.StringType
                }
            )
        ) {
            TvShowDetailsScreen()
        }
    }
}