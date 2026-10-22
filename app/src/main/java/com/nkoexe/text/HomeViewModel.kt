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
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HistoryRepository(application)
    private val operations = Mutex()

    private val _history = MutableStateFlow<List<HistoryRecord>>(emptyList())
    val history: StateFlow<List<HistoryRecord>> = _history.asStateFlow()

    init {
        refreshHistory()
    }

    fun refreshHistory() {
        viewModelScope.launch {
            operations.withLock {
                _history.value = withContext(Dispatchers.IO) { repository.all() }
            }
        }
    }

    fun recordDisplayed(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            operations.withLock {
                _history.value = withContext(Dispatchers.IO) {
                    repository.record(text)
                    repository.all()
                }
            }
        }
    }

    fun deleteHistory(text: String) {
        viewModelScope.launch {
            operations.withLock {
                _history.value = withContext(Dispatchers.IO) {
                    repository.delete(text)
                    repository.all()
                }
            }
        }
    }

    override fun onCleared() {
        repository.close()
        super.onCleared()
    }
}
