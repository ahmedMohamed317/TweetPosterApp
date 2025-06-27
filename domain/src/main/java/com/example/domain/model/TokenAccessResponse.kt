package com.example.domain.model

data class TokenAccessResponse(
    val tokenType: String,
    val accessToken: String,
    val expiresIn: Int,
    val scope: String
)
