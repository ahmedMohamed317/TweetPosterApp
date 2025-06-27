package com.example.data.repository

import com.example.data.source.remote.dto.response.TweetDataDto
import com.example.data.source.remote.dto.response.TweetDto
import com.example.data.source.remote.network.TweetService
import com.example.domain.model.request.TweetRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class TweetRepositoryImplTest {

    private lateinit var tweetRepository: TweetRepositoryImp
    private lateinit var tweetService: TweetService

    @Before
    fun setup() {
        tweetService = mockk()
        tweetRepository = TweetRepositoryImp(tweetService)
    }

    @Test
    fun `postTweet() should call API and return TweetResponse`() = runTest {
        // Given
        val token = "Bearer token"
        val tweetText = "Teest"
        val request = TweetRequest(tweetText)
        val tweetDto = TweetDto(TweetDataDto(id = "123", text = tweetText))
        val expected = com.example.domain.model.TweetResponse(
            com.example.domain.model.TweetDataResponse(id = "123", text = tweetText)
        )

        coEvery { tweetService.postTweet(token, request) } returns Response.success(tweetDto)

        // When
        val result = tweetRepository.postTweet(token, request)

        // Then
        assertEquals(expected, result)
        coVerify(exactly = 1) { tweetService.postTweet(token, request) }
    }
}
