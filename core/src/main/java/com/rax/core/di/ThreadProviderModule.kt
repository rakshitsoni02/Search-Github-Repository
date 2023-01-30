package com.rax.core.di

import com.rax.core.threading.DispatcherProvider
import com.rax.core.threading.DispatcherProviderType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ThreadProviderModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface ThreadAbstractModule {
        @Binds
        fun bindDispatcherProvider(impl: DispatcherProvider): DispatcherProviderType
    }
}
