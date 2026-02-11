package com.markoapps.aisummerizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoapps.aisummerizer.data.local.AiLocalDataSource
import com.markoapps.aisummerizer.domain.model.NoteSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HistoryUiState(
    val isLoading: Boolean = false,
    val items: List<NoteSummary> = emptyList(),
    val error: String? = null
)

class HistoryViewModel(
    private val localDataSource: AiLocalDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryUiState())
    val state = _state.asStateFlow()

    fun loadHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val history = localDataSource.getHistory()
                _state.update {
                    it.copy(isLoading = false, items = history)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }
}