package com.example.cryptocoinstest.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineRule(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Dispatchers.setMain(dispatcher) // Set TestCoroutineDispatcher to Dispatchers.Main
                try {
                    base.evaluate() // Run the tests
                } finally {
                    Dispatchers.resetMain() // Reset to original Main dispatcher
                    dispatcher.cleanupTestCoroutines() // Clean up
                }
            }
        }
    }
}