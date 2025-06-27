package com.example.city_input.ui

import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.example.city_input.ui.CodeGenerator.generateCodeChallenge
import com.example.city_input.ui.CodeGenerator.generateCodeVerifier
import com.example.core.base.BaseViewModel
import com.example.core.util.DispatcherProvider
import com.example.core.util.ErrorHandler
import com.example.domain.model.TokenAccessResponse
import com.example.domain.model.TweetResponse
import com.example.domain.model.request.TweetRequest
import com.example.domain.usecase.GetAccessTokenUseCase
import com.example.domain.usecase.PostTweetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val postTweetUseCase: PostTweetUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel<TweetUiState>(TweetUiState()) {
    private val _authUrlFlow = MutableSharedFlow<String>()
    val authUrlFlow = _authUrlFlow

    init {
        viewModelScope.launch {
            delay(4000)
            openTwitterAuthPage()
        }
    }

    fun getAccessToken(
        code: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
    ) {
        viewModelScope.launch {
            tryToExecute(
                { getAccessTokenUseCase(
                    code,
                    clientId,
                    redirectUri,
                    codeVerifier,
                ) },
                ::getAccessTokenSuccess,
                ::screenError,
                dispatcherProvider.io
            )
        }
    }

    private fun getAccessTokenSuccess(response: TokenAccessResponse) {
        updateState {
            it.copy(
                isLoading = false,
                isError = false,
                accessToken = response.accessToken,
            )
        }
    }

    fun postTweet(bearer: String) {
        updateState { it.copy(isLoading = true, isError = false, error = null) }
        viewModelScope.launch {
            tryToExecute(
                { postTweetUseCase(bearer, TweetRequest(text = state.value.tweetText)) },
                ::postTweetSuccess,
                ::screenError,
                dispatcherProvider.io
            )
        }
    }


    private fun postTweetSuccess(response: TweetResponse) {
        updateState {
            it.copy(
                isLoading = false,
                isError = false,
                tweetState = response.data.toTweetState()
            )
        }
    }

    private fun screenError(error: ErrorHandler) {
        updateState { it.copy(isLoading = false, error = error, isError = true) }
    }

    private fun updateState(update: (TweetUiState) -> TweetUiState) {
        _state.update(update)
    }

    fun onTweetTextChange(newText: String) {
        _state.update { it.copy(tweetText = newText) }
    }

    private fun openTwitterAuthPage() {
        val verifier = generateCodeVerifier()
        updateState { it.copy(code = verifier) }
        val challenge = generateCodeChallenge(verifier)
        val uri = "https://twitter.com/i/oauth2/authorize".toUri().buildUpon()
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("client_id", "SndLU2k0S3RtVGJxWWhpZ1FuZV86MTpjaQ")
            .appendQueryParameter("redirect_uri", "tweetposter://callback")
            .appendQueryParameter("scope", "tweet.read tweet.write users.read offline.access")
            .appendQueryParameter("state", "state")
            .appendQueryParameter("code_challenge", challenge)
            .appendQueryParameter("code_challenge_method", "S256")
            .build()
            .toString()

        viewModelScope.launch {
            _authUrlFlow.emit(uri)
        }
    }
}
