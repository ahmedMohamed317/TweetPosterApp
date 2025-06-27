package com.example.domain.usecase

import com.example.domain.model.TweetResponse
import com.example.domain.model.request.TweetRequest
import com.example.domain.repository.TweetRepository
import javax.inject.Inject

class PostTweetUseCase @Inject constructor(
    private val repository: TweetRepository
) {
    suspend operator fun invoke(bearer: String, tweetRequest: TweetRequest): TweetResponse{
        return repository.postTweet(bearer,tweetRequest)
    }
}
