package com.wiseowl.woli.ui.event

import android.content.Intent
import com.wiseowl.woli.ui.navigation.Screen

interface Action{
    data class Progress(val show: Boolean) : Action
    data class Navigate(val screen: Screen, val params: Map<String, String>? = null) : Action{
        fun toRoute() =   "$screen${if(!params.isNullOrEmpty())"?" + params.entries.joinToString("&") { "${it.key}=${it.value}" } else ""}"
    }
    data class Pop(val screen: Screen, val inclusive: Boolean = true): Action
    data class StartActivity(val intent: Intent): Action
    data class SnackBar(val text: String): Action
    object Logout: Action
    object None: Action
}

class UnhandledActionException(action: Action) : Exception("Unhandled action: $action")