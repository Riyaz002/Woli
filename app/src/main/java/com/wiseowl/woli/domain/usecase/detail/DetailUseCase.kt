package com.wiseowl.woli.domain.usecase.detail

data class DetailUseCase(
    val getImageUseCase: GetImageUseCase,
    val getBitmapUseCase: GetBitmapUseCase,
    val setWallpaperUseCase: SetWallpaperUseCase,
    val getImageDominantColorUseCase: GetImageDominantColorUseCase
)