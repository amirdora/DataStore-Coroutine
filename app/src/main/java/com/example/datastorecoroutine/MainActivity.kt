package com.example.datastorecoroutine


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.datastorecoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private  var _prefManager: UserPreferencesManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
        // get saved data store
        _prefManager = UserPreferencesManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val retrievedValue = _prefManager?.readSavedDataStore()
            withContext(context = Dispatchers.Main) {
                _binding?.textView?.text = retrievedValue
            }
        }
        _binding?.button?.setOnClickListener {
            val editTextValue = _binding?.editText?.text
            CoroutineScope(Dispatchers.IO).launch {
                _prefManager?.saveDataStore(editTextValue.toString())

                val retrievedValue = _prefManager?.readSavedDataStore()

                withContext(context = Dispatchers.Main) {
                    _binding?.textView?.text = retrievedValue
                }
            }
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}