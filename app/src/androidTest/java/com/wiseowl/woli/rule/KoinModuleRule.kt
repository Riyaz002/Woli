package com.wiseowl.woli.rule

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module


/**
 * Koin module rule for JUnit 4
 * @param modules list of Koin modules to load
 */
class KoinModuleRule(val modules: List<Module>): TestWatcher() {

    constructor(module: Module): this(listOf(module))

    override fun starting(description: Description?) {
        stopKoin() //Stop the already started Koin application
        startKoin { modules(modules) }
        super.starting(description)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        stopKoin()
    }
}