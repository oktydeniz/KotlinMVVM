package com.oktydeniz.kotlincountries.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.oktydeniz.kotlincountries.model.CountriesModel
import com.oktydeniz.kotlincountries.room.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {
    val countryModel = MutableLiveData<CountriesModel>()
    fun getDataFromRoom(uui: Int) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val model = dao.getCountry(uui)
            countryModel.value = model
        }
    }
}