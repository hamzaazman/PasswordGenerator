package com.hamzaazman.passwordgenerator.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamzaazman.passwordgenerator.data.model.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 2, exportSchema = false)
abstract class MainRoomDB : RoomDatabase() {
    abstract fun mainDao(): MainDao
}