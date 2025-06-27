package com.example.tweetposterapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.city_input.ui.AuthStorage
import com.example.city_input.ui.TweetViewModel
import com.example.core.theme.TweetPosterAppTheme
import com.example.tweetposterapp.navigation.MainNavGraph
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val tweetViewModel: TweetViewModel = hiltViewModel()
            handleTwitterRedirect(intent,tweetViewModel)
            LaunchedEffect(Unit) {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    tweetViewModel.authUrlFlow.collect { url ->
                        if (intent.data == null) {
                            AuthStorage.saveCodeVerifier(this@MainActivity, tweetViewModel.state.value.code)
                            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                            startActivity(intent)
                        }
                    }
                }
            }

            TweetPosterAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavGraph(
                        tweetViewModel,
                    )
                }
            }
        }
    }

    private fun handleTwitterRedirect(intent: Intent?, tweetViewModel: TweetViewModel) {
        val data = intent?.data
        if (data != null && data.toString().startsWith("tweetposter://callback")) {
            val code = data.getQueryParameter("code")
            Log.d("Auth", "Received code: $code")

            val codeVerifier = AuthStorage.getCodeVerifier(this)
            Log.d("Auth", "Loaded verifier: $codeVerifier")

            if (!code.isNullOrEmpty() && !codeVerifier.isNullOrEmpty()) {
                tweetViewModel.getAccessToken(
                    code = code,
                    clientId = "SndLU2k0S3RtVGJxWWhpZ1FuZV86MTpjaQ",
                    redirectUri = "tweetposter://callback",
                    codeVerifier = codeVerifier
                )
                AuthStorage.clear(this)
            } else {
                Log.e("Auth", "Missing code or verifier")
            }
        }
    }
}
