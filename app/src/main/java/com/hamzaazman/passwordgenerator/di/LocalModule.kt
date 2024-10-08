package com.hamzaazman.passwordgenerator.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamzaazman.passwordgenerator.data.source.local.MainDao
import com.hamzaazman.passwordgenerator.data.source.local.MainRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            MainRoomDB::class.java,
            MainRoomDB::class.simpleName
        ).build()
    }

    @Provides
    fun provideMainDao(database: MainRoomDB): MainDao = database.mainDao()
}