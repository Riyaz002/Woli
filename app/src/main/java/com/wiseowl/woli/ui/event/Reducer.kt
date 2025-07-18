package com.wiseowl.woli.ui.event

import kotlin.reflect.KClass



/**
 * Reducer that envelops the logic for set of instruction to execute for [Action]
 * @param cases [Map] of [Action] -> [Function]
 * @since version 22
 */
class Reducer(
    val cases: MutableMap<KClass<out Action>, (Action) -> Unit> = mutableMapOf()
){
    /**
     * Executes the logic for the given [Action], if a handler exists.
     * @return true if a handler was found and executed, false otherwise.
     */
    fun handle(action: Action): Boolean {
        val handler = cases[action::class] ?: return false
        handler(action)
        return true
    }
}

/**
 * [Reducer] builder class that uses lambda with receiver.
 * @since version 22
 */
class ReducerBuilder {
    val cases = mutableMapOf<KClass<out Action>, (Action) -> Unit>()

    /**
     * Utility function to define your handler for an [Action]
     */
    inline fun <reified A : Action> on(noinline block: (A) -> Unit) {
        cases[A::class] = { action -> block(action as A) }
    }

    fun build(): Reducer = Reducer(cases)
}

/**
 * Helper function to create reducer with the [ReducerBuilder] lambda.
 * @since version 22
 */
fun reducerOf(init: ReducerBuilder.() -> Unit): Reducer {
    return ReducerBuilder().apply(init).build()
}