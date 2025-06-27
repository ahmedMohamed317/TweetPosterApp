package com.example.data.source.remote.mapper

import com.example.data.source.remote.dto.response.TokenAccessDto
import com.example.data.source.remote.dto.response.TweetDataDto
import com.example.data.source.remote.dto.response.TweetDto
import com.example.domain.model.TokenAccessResponse
import com.example.domain.model.TweetDataResponse
import com.example.domain.model.TweetResponse

fun TweetDto.toTweetResponse(): TweetResponse {
    return TweetResponse(
        data = this.data.toTweetDataResponse()
    )
}

fun TweetDataDto?.toTweetDataResponse(): TweetDataResponse {
    return TweetDataResponse(
        id = this?.id ?: "",
        text = this?.text ?: ""
    )
}

fun TokenAccessDto.toTokenAccessResponse():TokenAccessResponse{
    return TokenAccessResponse(
        tokenType = this.tokenType ?: "",
        accessToken = this.accessToken ?: "",
        expiresIn = this.expiresIn ?: 0,
        scope = this.scope ?: "")
}
