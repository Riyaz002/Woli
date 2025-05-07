package com.wiseowl.woli

import com.wiseowl.woli.domain.event.ActionHandler
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.Rule
import com.wiseowl.woli.domain.event.Action

/**
 * [Rule] to avoid [NullPointerException] while testing any class that fires [Action] internally.
 */
class ActionHandlerRule: TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        ActionHandler.listen {

        }
    }
}