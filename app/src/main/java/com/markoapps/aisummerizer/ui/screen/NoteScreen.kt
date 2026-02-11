package com.markoapps.aisummerizer.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markoapps.aisummerizer.domain.model.NoteSummary
import com.markoapps.aisummerizer.domain.model.SummaryMode
import com.markoapps.aisummerizer.viewmodel.SummarizerUiState
import com.markoapps.aisummerizer.viewmodel.NoteViewModel

@Composable
fun NoteScreenRoute(
    viewModel: NoteViewModel,
) {
    val state by viewModel.state.collectAsState()

    NoteScreen(
        state = state,
        onNoteChange = viewModel::updateNote,
        onSummarizeClick = { mode ->
            viewModel.summarize(mode)
        }
    )
}

@Composable
fun NoteScreen(
    state: SummarizerUiState,
    onNoteChange: (String) -> Unit,
    onSummarizeClick: (SummaryMode) -> Unit
) {
    var selectedMode by remember { mutableStateOf(SummaryMode.SHORT) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "AI Note Summarizer",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.note,
            onValueChange = onNoteChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            label = { Text("Paste your note here") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Summary Mode:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ModeButton(
                text = "TLDR",
                selected = selectedMode == SummaryMode.TLDR,
                onClick = { selectedMode = SummaryMode.TLDR }
            )

            ModeButton(
                text = "Short",
                selected = selectedMode == SummaryMode.SHORT,
                onClick = { selectedMode = SummaryMode.SHORT }
            )

            ModeButton(
                text = "Detailed",
                selected = selectedMode == SummaryMode.DETAILED,
                onClick = { selectedMode = SummaryMode.DETAILED }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSummarizeClick(selectedMode) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading && state.note.isNotBlank()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Summarizing...")
            } else {
                Text("Summarize")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        state.summary?.let { summary ->
            SummaryResult(summary)
        }
    }
}

@Composable
fun ModeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors =
        if (selected) ButtonDefaults.buttonColors()
        else ButtonDefaults.outlinedButtonColors()

    if (selected) {
        Button(onClick = onClick, colors = colors) {
            Text(text)
        }
    } else {
        OutlinedButton(onClick = onClick, colors = colors) {
            Text(text)
        }
    }
}

@Composable
fun SummaryResult(summary: NoteSummary) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "TLDR:",
            style = MaterialTheme.typography.titleMedium
        )
        Text(summary.tldr)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Summary:",
            style = MaterialTheme.typography.titleMedium
        )
        Text(summary.summary)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Action Items:",
            style = MaterialTheme.typography.titleMedium
        )
        summary.actionItems.forEach { item ->
            Text("• $item")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Deadlines:",
            style = MaterialTheme.typography.titleMedium
        )
        summary.deadlines.forEach { deadline ->
            Text("• $deadline")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Tags:",
            style = MaterialTheme.typography.titleMedium
        )
        Text(summary.tags.joinToString(", "))
    }
}