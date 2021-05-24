package com.oktydeniz.kotlincountries.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oktydeniz.kotlincountries.model.CountriesModel

@Database(entities = [CountriesModel::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        // we will call this different threads
        @Volatile
        private var instance: CountryDatabase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDB(context).also {
                instance = it
            }
        }

        private fun makeDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CountryDatabase::class.java,
                "countrydatabase"
            ).build()
    }
}