package com.example.city_input.ui

import android.content.Context
import android.content.Intent
import android.util.Base64
import android.util.Log
import androidx.core.net.toUri
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.security.MessageDigest
import java.util.UUID
import androidx.core.content.edit

object CodeGenerator {

    private const val TAG = "CodeGenerator"
    private const val PREFS_NAME = "auth_prefs"
    private const val KEY_VERIFIER = "code_verifier"

    fun generateCodeVerifier(): String {
        val verifier = UUID.randomUUID().toString().replace("-", "")
        Log.d(TAG, "Generated code_verifier: $verifier")
        return verifier
    }

    fun generateCodeChallenge(verifier: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(verifier.toByteArray())
        val challenge = Base64.encodeToString(
            bytes,
            Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
        )
        Log.d(TAG, "Generated code_challenge: $challenge")
        return challenge
    }

    private fun saveCodeVerifier(context: Context, verifier: String) {
        Log.d(TAG, "Saving code_verifier to SharedPreferences")
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit() {
                putString(KEY_VERIFIER, verifier)
            }
    }

    fun getCodeVerifier(context: Context): String? {
        val verifier = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_VERIFIER, null)
        Log.d(TAG, "Retrieved code_verifier from SharedPreferences: $verifier")
        return verifier
    }

    fun clearVerifier(context: Context) {
        Log.d(TAG, "Clearing stored code_verifier from SharedPreferences")
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit() {
                remove(KEY_VERIFIER)
            }
    }

    fun openTwitterAuthPage(context: Context) {
        val verifier = generateCodeVerifier()
        saveCodeVerifier(context, verifier)

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

        Log.d(TAG, "Opening Twitter auth page: $uri")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    suspend fun getAccessToken(code: String, codeVerifier: String): String {
        val client = OkHttpClient()
        Log.d("CodeGenerator", "Response code:0")

        val form = FormBody.Builder()
            .add("code", code)
            .add("grant_type", "authorization_code")
            .add("client_id", "SndLU2k0S3RtVGJxWWhpZ1FuZV86MTpjaQ")
            .add("redirect_uri", "tweetposter://callback")
            .add("code_verifier", codeVerifier)
            .build()
        Log.d("CodeGenerator", "Response code:1")

        val request = Request.Builder()
            .url("https://api.twitter.com/2/oauth2/token")
            .post(form)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()
        Log.d("CodeGenerator", "Response code:2")


        try {
            val response = client.newCall(request).execute()
            val body = response.body?.string()

            Log.d("CodeGenerator", "Response code: ${response.code}")
            Log.d("CodeGenerator", "Response body: $body")

            if (!response.isSuccessful || body == null) {
                throw Exception("Token exchange failed: ${response.code} - $body")
            }

            val json = JSONObject(body)
            return json.getString("access_token")
        } catch (e: Exception) {
            Log.e("CodeGenerator", "Error during token request", e)
            throw e
        }

    }

    fun postTweet(accessToken: String, message: String) {
        Log.d(TAG, "Posting tweet with access token: $accessToken")
        val client = OkHttpClient()

        val body = JSONObject().apply {
            put("text", message)
        }

        val requestBody = body.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://api.twitter.com/2/tweets")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Tweet failed: ${e.message}", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d(TAG, "Tweet posted. Response: $responseBody")
            }
        })
    }
}
