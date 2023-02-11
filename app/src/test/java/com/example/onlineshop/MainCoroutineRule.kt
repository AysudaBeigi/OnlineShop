package com.example.onlineshop

import com.example.onlineshop.base.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MainCoroutineRule : TestRule {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)
                base.evaluate()
                Dispatchers.resetMain()
                testCoroutineDispatcher.cleanupTestCoroutines()
            }

        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        testCoroutineDispatcher.runBlockingTest(block)
    }

    fun getDispatcher(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }

}

fun createTestDispatcherProvider(coroutineDispatcher: CoroutineDispatcher): CoroutineDispatcherProvider =
    object : CoroutineDispatcherProvider {
        override fun bgDispatcher() = coroutineDispatcher
        override fun ioDispatcher() = coroutineDispatcher
        override fun uiDispatcher() = coroutineDispatcher
    }

fun CoroutineDispatcher.asDispatcherProvider(): CoroutineDispatcherProvider =
    createTestDispatcherProvider(this)