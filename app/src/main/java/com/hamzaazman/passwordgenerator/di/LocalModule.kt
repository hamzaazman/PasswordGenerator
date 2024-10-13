package com.hamzaazman.passwordgenerator.di

import android.content.Context
import androidx.room.Room
import com.hamzaazman.passwordgenerator.data.source.local.MainDao
import com.hamzaazman.passwordgenerator.data.source.local.MainRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): MainRoomDB {
        return Room.databaseBuilder(
            context,
            MainRoomDB::class.java,
            MainRoomDB::class.simpleName
        ).fallbackToDestructiveMigrationFrom().build()
    }

    @Provides
    fun provideMainDao(mainRoomDB: MainRoomDB): MainDao {
        return mainRoomDB.mainDao()
    }

}