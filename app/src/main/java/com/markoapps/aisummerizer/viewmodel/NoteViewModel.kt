package com.markoapps.aisummerizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoapps.aisummerizer.domain.model.NoteSummary
import com.markoapps.aisummerizer.domain.model.SummaryMode
import com.markoapps.aisummerizer.domain.usecase.SummarizeNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SummarizerUiState(
    val note: String = "",
    val isLoading: Boolean = false,
    val summary: NoteSummary? = null,
    val error: String? = null
)

class NoteViewModel(
    private val summarizeNote: SummarizeNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SummarizerUiState())
    val state = _state.asStateFlow()

    fun updateNote(text: String) {
        _state.update { it.copy(note = text) }
    }

    fun summarize(mode: SummaryMode) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val result = summarizeNote(_state.value.note, mode)
                _state.update { it.copy(isLoading = false, summary = result) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}