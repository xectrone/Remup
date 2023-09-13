package com.example.remup.data.hilt.di

import android.app.Application
import androidx.room.Room
import com.example.remup.data.database.AppDatabase
import com.example.remup.data.database.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule
{
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase
    {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppRepository(database: AppDatabase): AppRepository
    {
        return AppRepository(database.dao)
    }






}