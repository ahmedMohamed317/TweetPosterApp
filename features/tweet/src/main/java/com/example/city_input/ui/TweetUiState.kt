package com.example.city_input.ui

import com.example.core.util.ErrorHandler
import com.example.domain.model.TweetDataResponse
import com.example.domain.model.TweetResponse

data class  TweetUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isError: Boolean = false,
    val tweetText: String = "sad",
    val accessToken: String = "",
    val code: String = "",
    val tweetState: TweetState = TweetState(),
)

data class TweetState(
    val id: String = "",
    val text: String = "",
)

fun TweetDataResponse.toTweetState() = TweetState(
    id = this.id,
    text = this.text,
)

