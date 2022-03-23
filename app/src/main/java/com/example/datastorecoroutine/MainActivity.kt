package com.example.datastorecoroutine

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastorecoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val EXAMPLE_KEY = stringPreferencesKey("myKey")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
        // get saved data store
        readSavedDataStore()

        _binding?.button?.setOnClickListener {
            val editTextValue = _binding?.editText?.text
                saveDataStore(editTextValue.toString())
        }
    }

    private fun saveDataStore(editTextValue: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { settings ->
                settings[EXAMPLE_KEY] = editTextValue
            }
        }
    }

    private fun readSavedDataStore() {
         CoroutineScope(Dispatchers.IO).launch {
            dataStore.data
                .map { preferences ->
                  _binding?.textView?.text =  preferences[EXAMPLE_KEY]
                }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}