package com.example.datastorecoroutine

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Note: This is at the top level of the file, outside of any classes.
val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreferencesManager(context: Context) {
    private val dataStore = context.dataStore
    private val EXAMPLE_KEY = stringPreferencesKey("myKey")

     suspend fun saveDataStore(editTextValue: String) {
        dataStore.edit { settings ->
            settings[EXAMPLE_KEY] = editTextValue
        }
    }


     suspend fun readSavedDataStore() : String? {
        val preferences = dataStore.data.first()
        return preferences[EXAMPLE_KEY]
    }
}