package com.oktydeniz.kotlincountries.service

import com.oktydeniz.kotlincountries.model.CountriesModel
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    //https://raw.githubusercontent.com/
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<CountriesModel>>
}