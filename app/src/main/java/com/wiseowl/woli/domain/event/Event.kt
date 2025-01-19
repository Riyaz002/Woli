package com.wiseowl.woli.domain.event

import com.wiseowl.woli.ui.navigation.Screen

sealed interface Event{
    data class Progress(val show: Boolean) : Event
    data class Navigate(val screen: Screen, val params: Map<String, String>) : Event{
        fun toRoute() =   "$screen?${params.entries.joinToString("&") { "${it.key}=${it.value}" }}"
    }
}