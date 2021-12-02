package com.unimapp.data.di

import com.unimapp.data.di.annotations.EncryptedSharedPrefs
import com.unimapp.data.di.annotations.MainSharedPrefs
import com.unimapp.data.prefs.base.BasePreferences
import com.unimapp.data.prefs.sharedprefs.EncryptedSharedPreference
import com.unimapp.data.prefs.sharedprefs.MainSharedPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SharedPrefsModule {

    @Binds
    @MainSharedPrefs
    @Singleton
    fun bindMainSharedPrefs(mainSharedPreferences: MainSharedPreferences): BasePreferences

    @Binds
    @EncryptedSharedPrefs
    @Singleton
    fun bindEncryptedSharedPrefs(encryptedSharedPreference: EncryptedSharedPreference): BasePreferences

}