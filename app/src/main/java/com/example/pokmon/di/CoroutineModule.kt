package com.example.pokmon.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Module
@InstallIn(ActivityComponent::class)
class CoroutineModule {

    @Provides
    @ActivityScoped
    fun provideCoroutineJob(): Job {
        return Job()
    }

    @Provides
    @ActivityScoped
    fun providesCoroutineScope(job: Job): CoroutineScope {
        return CoroutineScope(Dispatchers.Main + job)
    }
}