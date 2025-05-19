package com.wiseowl.woli.domain.event

sealed class Event {
    object Logout: Event()
}