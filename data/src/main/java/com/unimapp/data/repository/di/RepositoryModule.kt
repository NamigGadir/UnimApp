package com.unimapp.data.repository.di

import com.unimapp.data.repository.onboarding.OnBoardingRepository
import com.unimapp.data.repository.onboarding.OnBoardingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindOnboardingRepository(onBoardingRepository: OnBoardingRepositoryImpl):OnBoardingRepository
}