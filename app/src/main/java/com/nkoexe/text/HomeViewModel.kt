package com.nkoexe.text

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nkoexe.text.data.HistoryRecord
import com.nkoexe.text.data.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HistoryRepository(application)
    private val _history = MutableStateFlow<List<HistoryRecord>>(emptyList())
    val history: StateFlow<List<HistoryRecord>> = _history.asStateFlow()

    init { refreshHistory() }

    fun refreshHistory() {
        viewModelScope.launch {
            _history.value = withContext(Dispatchers.IO) { repository.all() }
        }
    }

    fun recordDisplayed(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.record(text)
            val updated = repository.all()
            withContext(Dispatchers.Main) { _history.value = updated }
        }
    }

    fun deleteHistory(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(text)
            val updated = repository.all()
            withContext(Dispatchers.Main) { _history.value = updated }
        }
    }
}
