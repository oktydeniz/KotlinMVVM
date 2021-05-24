package com.oktydeniz.kotlincountries.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oktydeniz.kotlincountries.model.CountriesModel

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries: CountriesModel): List<Long>

    @Query("SELECT * FROM countriesModel")
    suspend fun getCountries(): List<CountriesModel>

    @Query("SELECT * FROM countriesModel WHERE uuid = :id")
    suspend fun getCountry(id: Int): CountriesModel

    @Query("DELETE FROM countriesModel ")
    suspend fun deleteAll()
}