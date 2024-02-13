package com.jhoangamarral.ticketmasterchallenge.presentation.ui.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jhoangamarral.ticketmasterchallenge.util.CoroutineTestRule
import com.jhoangamarral.ticketmasterchallenge.util.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Rule

open class BaseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun runTest(block: suspend TestScope.() -> Unit) = coroutineRule.runTest(block)
}