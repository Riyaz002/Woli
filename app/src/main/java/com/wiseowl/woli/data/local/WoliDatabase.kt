package com.wiseowl.woli.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wiseowl.woli.data.local.entity.ImageDTO

@Database(entities = [ImageDTO::class], version = 1)
abstract class WoliDatabase: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object{
        @Volatile private var INSTANCE: WoliDatabase? = null
        private const val NAME = "woli.db"

        fun getInstance(context: Context): WoliDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(context, WoliDatabase::class.java, NAME).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}