package com.example.tweetposterapp.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.city_input.ui.TweetScreen
import com.example.city_input.ui.TweetViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainNavGraph(
    tweetViewModel: TweetViewModel,
) {
    val navController = rememberNavController()
    val state = tweetViewModel.state.collectAsState()
    NavHost(
        modifier = Modifier.semantics { testTagsAsResourceId = true },
        navController = navController,
        startDestination = Screen.CityInput.route,
    ) {
        composable(
            route = Screen.CityInput.route
        ) {

            TweetScreen(
                state = state.value,
                modifier = Modifier.background(Color.White),
                tweetViewModel::onTweetTextChange
            ){
                Log.d("access_token",state.value.accessToken)
                tweetViewModel.postTweet(
                    bearer ="Bearer ${state.value.accessToken}",
                )
            }
        }

    }
}