package com.markoapps.aisummerizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoapps.aisummerizer.data.local.AiLocalDataSource
import com.markoapps.aisummerizer.data.repository.AiRepository
import com.markoapps.aisummerizer.domain.model.NoteSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SummaryViewModel(
    private val noteId: String,
    private val localDataSource: AiLocalDataSource
) : ViewModel() {

    private val _summary = MutableStateFlow<NoteSummary?>(null)
    val summary: StateFlow<NoteSummary?> = _summary.asStateFlow()

    init {
        viewModelScope.launch {
            _summary.value = localDataSource.getSummaryById(noteId)
        }
    }
}