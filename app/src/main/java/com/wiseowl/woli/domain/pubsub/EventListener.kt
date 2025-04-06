package com.wiseowl.woli.domain.pubsub

interface EventListener {
    fun pushEvent(event: Event)
}