package com.wiseowl.woli.domain.event

object EventHandler {
    private var mSubscriber: ((Event) -> Unit)? = null

    fun subscribe(subscriber: (Event) -> Unit){
        mSubscriber = subscriber
    }

    fun sendEvent(event: Event){
        mSubscriber?.invoke(event)
    }
}