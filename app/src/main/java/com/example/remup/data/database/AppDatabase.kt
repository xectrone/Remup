package com.example.remup.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.remup.data.model.Note


@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AppConverters::class)
abstract class AppDatabase: RoomDatabase()
{
    abstract val dao: AppDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}