package com.wiseowl.woli.data.event

import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.event.EventListener
import org.koin.java.KoinJavaComponent.inject

class EventListener: EventListener {
    private val sharedPreference by inject<EncryptedSharedPreference>(EncryptedSharedPreference::class.java)
    override fun pushEvent(event: Event) {
        when(event){
            Event.Logout -> {
                sharedPreference.clear()
            }
        }
    }
}