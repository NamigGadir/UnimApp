package com.unimapp.data.prefs.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.unimapp.data.prefs.base.BasePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class MainSharedPreferences @Inject constructor(@ApplicationContext context: Context) : BasePreferences() {

    companion object {
        const val PREF_NAME = "main_shared_pref"
    }

    override val filename: String
        get() = PREF_NAME

    override val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
    }

}