package com.unimapp.data.repository.di

import com.unimapp.data.repository.di.annotations.EncryptedSharedPrefs
import com.unimapp.data.repository.di.annotations.MainSharedPrefs
import com.unimapp.data.repository.prefs.base.BasePreferences
import com.unimapp.data.repository.prefs.sharedprefs.EncryptedSharedPreference
import com.unimapp.data.repository.prefs.sharedprefs.MainSharedPreferences
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