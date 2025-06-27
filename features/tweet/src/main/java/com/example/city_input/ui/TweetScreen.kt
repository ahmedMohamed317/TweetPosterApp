package com.example.city_input.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.composables.Loading
import com.example.core.composables.NetworkError
import com.example.core.util.ErrorHandler
import com.example.tweet.R
import android.icu.text.BreakIterator
import com.example.core.util.countGraphemes


@Composable
fun TweetScreen(
    state: TweetUiState,
    modifier: Modifier = Modifier,
    onTweetTextChange: (String) -> Unit,
    onPostTweetClick: () -> Unit,
    ) {
    TwitterCharacterCounterScreen(
        state = state,
        modifier = modifier.fillMaxSize(),
        onTweetTextChange = onTweetTextChange,
        onPostTweetClick = onPostTweetClick
    )
}


@Composable
fun Toolbar(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold

        )

        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
        )
    }
}

@Composable
fun TwitterCharacterCounterScreen(
    state: TweetUiState,
    modifier: Modifier = Modifier,
    onTweetTextChange: (String) -> Unit,
    onPostTweetClick: () -> Unit,
    ) {
    var tweetText by remember { mutableStateOf("") }
    val charLimit = 280
    val charsTyped = countGraphemes(tweetText)
    val charsRemaining = (charLimit - charsTyped).coerceAtLeast(0)
    val context = LocalContext.current
    LaunchedEffect(state.tweetState.id) {
        if (state.tweetState.id.isNotBlank()) {
            Toast.makeText(context, "Tweet posted successfully!", Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Loading(state = state.isLoading)
        NetworkError(state = state.isError, error= state.error ?: ErrorHandler.UnknownError("Something went wrong"))
        Toolbar(
            text = stringResource(id = com.example.core.R.string.twitter_character_count)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.twitter_logo),
            contentDescription = "Twitter Icon",
            tint = Color(0xFF1DA1F2),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(60.dp)

        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            CharacterBox(title = "Characters Typed", value = "$charsTyped/$charLimit")
            CharacterBox(title = "Characters Remaining", value = "$charsRemaining")
        }

        OutlinedTextField(
            value = tweetText,
            onValueChange = {
                if (it.length <= charLimit) {
                    tweetText = it
                    onTweetTextChange(it)
                    Log.d("tweet_text",state.tweetText)
                }
            },
            placeholder = { Text("Start typing! You can enter up to 280 characters") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            textStyle = TextStyle(fontSize = 14.sp),

        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val clipboardManager = LocalClipboardManager.current
            Button(
                onClick = {
                    clipboardManager.setText(AnnotatedString(tweetText))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Copy Text",color = Color.White)
            }

            Button(
                onClick = {
                    tweetText = ""
                    onTweetTextChange("")
                          },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
            ) {
                Text("Clear Text",color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onPostTweetClick()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2))
        ) {
            Text("Post tweet", color = Color.White , fontSize = 18.sp)
        }
    }
}

@Composable
fun CharacterBox(title: String, value: String) {
    Column(
        modifier = Modifier
            .width(170.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xffE6F6FE))
            .padding( 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp,modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
        Box(
            modifier = Modifier
                .width(168.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                .background(Color(0xffffffff))
                .padding(top = 12.dp, start = 2.dp, end = 2.dp, bottom = 2.dp),
        ) {
            Text(
                text = value,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TwitterCharacterCounterScreenPreview() {
    Text(text = "TweetScreen")
}


