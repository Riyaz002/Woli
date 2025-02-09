package com.wiseowl.woli.domain.event

import android.content.Intent
import com.wiseowl.woli.ui.navigation.Screen

interface Action{
    data class Progress(val show: Boolean) : Action
    data class Navigate(val screen: Screen, val params: Map<String, String>) : Action{
        fun toRoute() =   "$screen?${params.entries.joinToString("&") { "${it.key}=${it.value}" }}"
    }
    data class StartActivity(val intent: Intent): Action
}