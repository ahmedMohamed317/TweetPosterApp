package com.example.core.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.util.ErrorHandler
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NetworkError(
    state: Boolean,
    error: ErrorHandler
) {
    if (!state) return

    val (message, imageRes) = when (error) {
        is ErrorHandler.NetworkError -> error.message to R.drawable.network_connection_failure_backup
        is ErrorHandler.EmptyResponse -> error.message to R.drawable.general_error
        is ErrorHandler.ServerError -> error.message to R.drawable.server_error
        is ErrorHandler.UnknownError -> error.message to R.drawable.general_error
    }

    AnimatedVisibility(
        visible = state,
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically() + scaleIn(),
        exit = fadeOut(animationSpec = tween(500)) + slideOutVertically() + scaleOut()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(80.dp)
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}
