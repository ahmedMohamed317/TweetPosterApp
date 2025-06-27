package com.example.city_input.ui

import android.content.Context
import androidx.core.content.edit

object AuthStorage {

        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_VERIFIER = "code_verifier"

        fun saveCodeVerifier(context: Context, verifier: String) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit() {
                    putString(KEY_VERIFIER, verifier)
                }
        }

        fun getCodeVerifier(context: Context): String? {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_VERIFIER, null)
        }

        fun clear(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply()
        }
    }
