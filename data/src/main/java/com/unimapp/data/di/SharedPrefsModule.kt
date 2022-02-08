package com.unimapp.data.di

import android.content.Context
import com.unimapp.data.di.annotations.EncryptedSharedPrefs
import com.unimapp.data.di.annotations.MainSharedPrefs
import com.unimapp.data.prefs.base.BasePreferences
import com.unimapp.data.prefs.sharedprefs.AuthenticationPreferences
import com.unimapp.data.prefs.sharedprefs.EncryptedSharedPreference
import com.unimapp.data.prefs.sharedprefs.MainSharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [SharedPrefsModule.Binding::class])
@InstallIn(SingletonComponent::class)
object SharedPrefsModule {

    @Provides
    @Singleton
    fun provideAuthPreferences(@ApplicationContext context: Context) = AuthenticationPreferences(context)


    @Module
    @InstallIn(SingletonComponent::class)
    interface Binding {
        @Binds
        @MainSharedPrefs
        @Singleton
        fun bindMainSharedPrefs(mainSharedPreferences: MainSharedPreferences): BasePreferences

        @Binds
        @EncryptedSharedPrefs
        @Singleton
        fun bindEncryptedSharedPrefs(encryptedSharedPreference: EncryptedSharedPreference): BasePreferences
    }

}