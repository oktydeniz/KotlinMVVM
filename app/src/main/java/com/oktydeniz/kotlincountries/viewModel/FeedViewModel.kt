package com.oktydeniz.kotlincountries.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.oktydeniz.kotlincountries.model.CountriesModel
import com.oktydeniz.kotlincountries.room.CountryDatabase
import com.oktydeniz.kotlincountries.service.CountryAPIServices
import com.oktydeniz.kotlincountries.util.CustomSharedPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    val countries = MutableLiveData<List<CountriesModel>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    private val countryAPIServices = CountryAPIServices()
    private val customSharedPreference = CustomSharedPreference(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L
    private val disposable = CompositeDisposable()

    fun refreshData() {
        val updateTime = customSharedPreference.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataSQL()
        } else {
            getDataAPI()
        }

    }

    fun refreshFromAPI() {
        getDataAPI()
    }

    private fun getDataAPI() {
        countryLoading.value = true
        disposable.add(
            countryAPIServices.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountriesModel>>() {
                    override fun onSuccess(t: List<CountriesModel>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun getDataSQL() {
        countryLoading.value = false
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val dBList = dao.getCountries()
            showCountry(dBList)
        }
    }

    private fun showCountry(t: List<CountriesModel>) {
        countries.value = t
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<CountriesModel>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAll()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i++
            }
            showCountry(list)
        }
        customSharedPreference.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
