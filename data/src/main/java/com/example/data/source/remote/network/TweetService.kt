package com.example.data.source.remote.network

import com.example.domain.model.request.TweetRequest
import com.example.data.source.remote.dto.response.TokenAccessDto
import com.example.data.source.remote.dto.response.TweetDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface TweetService {

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getAccessToken(
        @Field("code") code: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code_verifier") codeVerifier: String
    ): Response<TokenAccessDto>

    @POST("tweets")
    suspend fun postTweet(
        @Header("Authorization") bearer: String,
        @Body body: TweetRequest
    ): Response<TweetDto>
}