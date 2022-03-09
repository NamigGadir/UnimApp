package com.unimapp.data.di

import com.unimapp.data.repository.auth.AuthRepositoryImpl
import com.unimapp.data.repository.feed.FeedRepositoryImpl
import com.unimapp.data.repository.notification.NotificationsRepositoryImpl
import com.unimapp.data.repository.onboarding.OnBoardingRepositoryImpl
import com.unimapp.domain.repository.AuthRepository
import com.unimapp.domain.repository.FeedRepository
import com.unimapp.domain.repository.NotificationsRepository
import com.unimapp.domain.repository.OnBoardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindOnboardingRepository(onBoardingRepository: OnBoardingRepositoryImpl): OnBoardingRepository

    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindFeedRepository(feedRepository: FeedRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    fun bindNotificationsRepository(notificationsRepositoryImpl: NotificationsRepositoryImpl): NotificationsRepository

}