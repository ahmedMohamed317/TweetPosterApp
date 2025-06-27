package com.example.domain.usecase

import com.example.domain.model.TokenAccessResponse
import com.example.domain.model.TweetResponse
import com.example.domain.model.request.TweetRequest
import com.example.domain.repository.TweetRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repository: TweetRepository
) {
    suspend operator fun invoke(
        code: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
    ): TokenAccessResponse{
        return repository.getAccessToken(
            code,
            clientId,
            redirectUri,
            codeVerifier
        )
    }
}
