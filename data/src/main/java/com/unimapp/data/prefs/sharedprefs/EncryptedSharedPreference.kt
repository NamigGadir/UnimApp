
package com.unimapp.data.prefs.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.unimapp.data.prefs.base.BasePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


open class EncryptedSharedPreference @Inject constructor(@ApplicationContext context: Context) : BasePreferences() {

    companion object {
        const val ENCRYPTED_PREF_NAME = "encrypted_shared_pref"
    }

    override val filename: String
        get() = ENCRYPTED_PREF_NAME

    override val prefs: SharedPreferences by lazy {
        val key = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        EncryptedSharedPreferences.create(
            context, filename, key,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}