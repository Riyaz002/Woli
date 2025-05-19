package com.wiseowl.woli.domain.event

interface EventListener {
    fun pushEvent(event: Event)
}