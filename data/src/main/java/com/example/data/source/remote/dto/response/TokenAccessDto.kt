package com.example.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName

data class TokenAccessDto(
    @SerializedName("token_type") val tokenType: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("expires_in") val expiresIn: Int?,
    @SerializedName("scope") val scope: String?
)