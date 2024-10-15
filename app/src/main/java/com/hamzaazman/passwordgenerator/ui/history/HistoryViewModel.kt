package com.hamzaazman.passwordgenerator.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzaazman.passwordgenerator.data.model.HistoryEntity
import com.hamzaazman.passwordgenerator.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _history = MutableStateFlow<HistoryState>(HistoryState())
    val history = _history
        .onStart { getAllHistory() }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = HistoryState()
        )

    private fun getAllHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getAllHistory().collect {
                _history.value = HistoryState(history = it)
            }
        }
    }

    private fun clearHistory() = viewModelScope.launch {
        if (_history.value.history.isNotEmpty()) {
            _history.value = HistoryState(history = emptyList())
            mainRepository.deleteAllHistory()
        }
    }

    fun performAction(action: Action) {
        when (action) {
            is Action.ClearHistory -> clearHistory()
            is Action.CopyPassword -> copyToClipboard(action.context, action.password)
        }
    }

    private fun copyToClipboard(context: android.content.Context, text: String) {
        val clipboard =
            context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("password", text)
        clipboard.setPrimaryClip(clip)
    }

}

data class HistoryState(
    val history: List<HistoryEntity> = emptyList(),
)

sealed class Action {
    data object ClearHistory : Action()
    data class CopyPassword(val password: String, val context: Context) : Action()
}
