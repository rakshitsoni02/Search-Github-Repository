package com.rax.core.di

import com.rax.core.logging.Logger
import com.rax.core.logging.TimberLogs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    internal fun provideLogger(): Logger = object : Logger {
        override fun log(message: () -> String) {
            TimberLogs.d(message)
        }

        override fun logError(throwable: () -> Throwable) {
            TimberLogs.e(throwable)
        }
    }
}
