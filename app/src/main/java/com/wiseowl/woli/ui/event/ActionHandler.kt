package com.wiseowl.woli.ui.event

object ActionHandler {
    private var mSubscriber: ((Action) -> Unit)? = null

    fun listen(subscriber: (Action) -> Unit){
        mSubscriber = subscriber
    }

    fun perform(action: Action){
        mSubscriber!!.invoke(action)
    }
}

fun Action.perform(){
    ActionHandler.perform(this)
}