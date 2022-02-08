package com.unimapp.data.prefs.sharedprefs

import android.content.Context

class AuthenticationPreferences(
    context: Context
) : MainSharedPreferences(context) {

    companion object {
        const val PREF_NAME = "auth_prefs"
        const val IS_REGISTERED = "is_registered"
        const val AUTH_TOKEN = "auth_token"
    }

    override val filename: String
        get() = PREF_NAME

    fun setIsRegistered(isRegistered: Boolean) {
        set(IS_REGISTERED, isRegistered)
    }

    fun isRegistered(): Boolean {
        return get(IS_REGISTERED, false)
    }

    fun setToken(token: String) {
        set(AUTH_TOKEN, token)
    }

    fun getToken(): String {
        return get(AUTH_TOKEN, "")
    }
}