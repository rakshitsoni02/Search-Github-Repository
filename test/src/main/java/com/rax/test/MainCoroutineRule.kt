package com.rax.test

import com.rax.core.threading.DispatcherProviderType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineRule : TestWatcher() {

    private val testDispatcher = UnconfinedTestDispatcher()
    val dispatcherProvider = object : DispatcherProviderType {

        override fun default(): CoroutineDispatcher = testDispatcher

        override fun io(): CoroutineDispatcher = testDispatcher

        override fun main(): CoroutineDispatcher = testDispatcher

        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
