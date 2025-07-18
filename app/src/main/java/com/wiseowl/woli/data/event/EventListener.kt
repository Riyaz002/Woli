package com.wiseowl.woli.data.event

import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.event.EventListener
import org.koin.java.KoinJavaComponent.inject

/**
 * Data level event listener
 * Get Events from other layer (UI) and perform action accordingly
 *
 * @author Riyaz Uddin
 * @since version 1
 */
class EventListener: EventListener {
    private val sharedPreference by inject<EncryptedSharedPreference>(EncryptedSharedPreference::class.java)

    /**
     * Push event to the event listener
     *
     * @param event Event to be pushed
     */
    override fun pushEvent(event: Event) {
        when(event){
            Event.Logout -> {
                sharedPreference.clear()
            }
        }
    }
}