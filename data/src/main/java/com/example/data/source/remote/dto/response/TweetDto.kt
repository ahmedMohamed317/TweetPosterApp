package com.example.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName


data class TweetDto(@SerializedName("data") val data: TweetDataDto?)

data class TweetDataDto(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text:String
)