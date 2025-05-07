package com.wiseowl.woli.domain.pubsub

sealed class Event {
    object Logout: Event()
}