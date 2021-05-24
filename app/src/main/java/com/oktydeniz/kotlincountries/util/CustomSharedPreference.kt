package com.oktydeniz.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreference {
    companion object {

        private const val PREFERENCE_TIME = "time"
        private var sharedPreference: SharedPreferences? = null

        @Volatile
        private var instance: CustomSharedPreference? = null
        private val lock = Any()
        operator fun invoke(context: Context): CustomSharedPreference =
            instance ?: synchronized(lock) {
                instance ?: makeCustomSharedPreference(context).also {
                    instance = it
                }
            }

        private fun makeCustomSharedPreference(context: Context): CustomSharedPreference {
            sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreference()
        }
    }

    fun saveTime(time: Long) {
        sharedPreference?.edit(commit = true) {
            putLong(PREFERENCE_TIME, time)
        }
    }

    fun getTime() = sharedPreference?.getLong(PREFERENCE_TIME, 0)
}