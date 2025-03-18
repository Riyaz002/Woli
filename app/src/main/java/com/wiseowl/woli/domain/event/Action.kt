package com.wiseowl.woli.domain.event

import android.content.Intent
import com.wiseowl.woli.ui.navigation.Screen

interface Action{
    data class Progress(val show: Boolean) : Action
    data class Navigate(val screen: Screen, val params: Map<String, String>? = null) : Action{
        fun toRoute() =   "$screen${if(!params.isNullOrEmpty())"?"+params.entries.joinToString("&") { "${it.key}=${it.value}" } else ""}"
    }
    data class StartActivity(val intent: Intent): Action
}