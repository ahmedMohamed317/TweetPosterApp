package com.example.data.repository

import android.util.Log
import com.example.data.source.remote.mapper.toTokenAccessResponse
import com.example.data.source.remote.mapper.toTweetResponse
import com.example.data.source.remote.network.TweetService
import com.example.domain.model.TokenAccessResponse
import com.example.domain.model.TweetResponse
import com.example.domain.model.request.TweetRequest
import com.example.domain.repository.TweetRepository
import javax.inject.Inject

class TweetRepositoryImp @Inject constructor(
    private val tweetService: TweetService
) : TweetRepository, BaseRepository() {
    override suspend fun postTweet(bearer: String, body: TweetRequest): TweetResponse {
        return wrapResponse {
            tweetService.postTweet(bearer, body)
        }.toTweetResponse()
    }

    override suspend fun getAccessToken(
        code: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String
    ): TokenAccessResponse {
        return wrapResponse {
            tweetService.getAccessToken(code = code,
                clientId = clientId,
                redirectUri = redirectUri,
                codeVerifier = codeVerifier
            )
        }.toTokenAccessResponse()
    }


}