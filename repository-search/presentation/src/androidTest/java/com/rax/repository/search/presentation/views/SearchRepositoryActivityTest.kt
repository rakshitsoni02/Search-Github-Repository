package com.rax.repository.search.presentation.views

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SearchRepositoryActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var scenario: ActivityScenario<SearchRepositoryActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(
            SearchRepositoryActivity::class.java
        )
    }

    @Test
    fun verifyViewModelInjection() {
        scenario.use {
            it.moveToState(Lifecycle.State.CREATED)
            it.onActivity { activity ->
                assertNotNull(activity.viewModel)
            }
        }
    }
}