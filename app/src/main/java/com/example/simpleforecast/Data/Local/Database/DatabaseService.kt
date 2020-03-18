package com.example.simpleforecast.Data.Local.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simpleforecast.Data.Local.Database.Dao.WeatherDao
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.Data.Local.Database.Entity.Weather

@Database(entities = [City::class, Weather::class], version = 1)
abstract class DatabaseService : RoomDatabase() {

    abstract fun weatherDao():WeatherDao


    companion object {
        @Volatile
        private var INSTANCE: DatabaseService? = null
        fun getInstance(context: Context): DatabaseService =
            INSTANCE
                ?: synchronized(DatabaseService::class.java){
                INSTANCE
                    ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseService::class.java,
                    "Weather.db"
                ).build()
                    .also { INSTANCE = it }
            }
    }
}
