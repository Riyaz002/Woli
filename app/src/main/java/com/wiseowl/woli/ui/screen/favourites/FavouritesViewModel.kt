package com.wiseowl.woli.ui.screen.favourites

import com.wiseowl.woli.domain.usecase.favourites.FavouritesUseCase
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.favourites.model.FavouritesModel
import java.util.Timer

class FavouritesViewModel(
    private val favouritesUseCase: FavouritesUseCase
): ScreenViewModel<FavouritesModel>({ FavouritesModel(favouritesUseCase.getFavourites()) }) {

    override val actionReducer: ReducerBuilder.() -> Unit
        get() = {

        }

    private val timers: HashMap<String, Timer> = hashMapOf()

    override fun onCleared() {
        super.onCleared()
        timers.forEach { it.value.cancel() }
        timers.clear()
    }
}