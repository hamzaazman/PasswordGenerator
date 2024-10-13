package com.hamzaazman.passwordgenerator.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzaazman.passwordgenerator.data.model.HistoryEntity
import com.hamzaazman.passwordgenerator.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    /*
    private val _history = MutableStateFlow<List<HistoryEntity>>(emptyList())
    val history: StateFlow<List<HistoryEntity>> get() = _history


     */

    private val _history = MutableStateFlow<List<HistoryEntity>>(emptyList())
    val history = _history
        .onStart { getAllHistory() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    private suspend fun getAllHistory() {
        _history.value = mainRepository.getAllHistory()
    }

}