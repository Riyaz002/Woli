package com.wiseowl.woli.ui.screen.favourites.model

import com.wiseowl.woli.domain.repository.media.model.Media

data class FavouritesModel(
    val favourites: List<Media> = emptyList(),
    val showRemoveDialog: Boolean = false,
    val mediaIdForRemoveDialog: Long? = null
)