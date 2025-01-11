package com.wiseowl.woli.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wiseowl.woli.data.local.entity.ImageDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun addAll(images: ImageDTO)

    @Query("SELECT * FROM image")
    fun getImages(): Flow<ImageDTO>

    @Query("Delete FROM image")
    fun clear()
}