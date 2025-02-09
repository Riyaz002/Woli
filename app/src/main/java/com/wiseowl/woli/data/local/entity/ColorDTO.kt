package com.wiseowl.woli.data.local.entity

import com.wiseowl.woli.domain.model.Color

data class ColorDTO(
    val primary: Int,
    val secondary: Int,
){
    companion object{
        fun ColorDTO.toColor(): Color = Color(
            primary,
            secondary
        )
        fun Color.toColorDTO() = ColorDTO(
            primary,
            secondary
        )
    }
}