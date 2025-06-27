package com.example.domain.repository

import com.example.domain.model.TokenAccessResponse
import com.example.domain.model.request.TweetRequest
import com.example.domain.model.TweetDataResponse
import com.example.domain.model.TweetResponse

interface TweetRepository {
    suspend fun postTweet(
        bearer: String,
        body: TweetRequest
    ): TweetResponse

    suspend fun getAccessToken(
        code: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
    ): TokenAccessResponse
}
