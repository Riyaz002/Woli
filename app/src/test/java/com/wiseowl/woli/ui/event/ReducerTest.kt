package com.wiseowl.woli.ui.event

import com.wiseowl.woli.ui.event.ReducerTest.TestAction.DefinedTestAction
import com.wiseowl.woli.ui.event.ReducerTest.TestAction.UnDefinedTestAction
import org.junit.Assert
import org.junit.Test

class ReducerTest {
    sealed class TestAction: Action{
        class DefinedTestAction: Action
        class UnDefinedTestAction: Action
    }

    val reducer = ReducerBuilder().apply {
        on<DefinedTestAction> { /** Does something */ }
    }.build()

    @Test
    fun `Reducer returns true for actions that has handler passed to it`(){
        val isHandled = reducer.handle(DefinedTestAction())
        Assert.assertTrue(isHandled)
    }

    @Test
    fun `Reducer returns false for actions that has no handler passed to it`(){
        val isHandled = reducer.handle(UnDefinedTestAction())
        Assert.assertFalse(isHandled)
    }
}