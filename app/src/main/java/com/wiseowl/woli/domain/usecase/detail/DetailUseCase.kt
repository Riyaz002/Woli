package com.wiseowl.woli.domain.usecase.detail

import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase

data class DetailUseCase(
    val mediaUseCase: MediaUseCase,
    val getBitmapUseCase: GetBitmapUseCase,
    val setWallpaperUseCase: SetWallpaperUseCase,
    val getComplementaryColorUseCase: GetComplementaryColorUseCase,
    val saveFileUseCase: SaveFileUseCase
)