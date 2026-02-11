package com.markoapps.aisummerizer.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markoapps.aisummerizer.domain.model.NoteSummary
import com.markoapps.aisummerizer.viewmodel.SummaryViewModel

@Composable
fun SummeryScreenRoute(
    viewModel: SummaryViewModel,
    onBack: () -> Unit
) {
    val summary = viewModel.summary.collectAsState().value

    summary?.let {
        SummaryScreen(
            summary = it,
            onBack = onBack
        )
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    summary: NoteSummary,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Summary") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // TLDR
            Text(
                text = "TLDR",
                style = MaterialTheme.typography.titleMedium
            )
            Text(summary.tldr)
            Spacer(modifier = Modifier.height(12.dp))

            // Full Summary
            Text(
                text = "Summary",
                style = MaterialTheme.typography.titleMedium
            )
            Text(summary.summary)
            Spacer(modifier = Modifier.height(12.dp))

            // Action Items
            if (summary.actionItems.isNotEmpty()) {
                Text(
                    text = "Action Items",
                    style = MaterialTheme.typography.titleMedium
                )
                summary.actionItems.forEach { item ->
                    Text("• $item")
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Deadlines
            if (summary.deadlines.isNotEmpty()) {
                Text(
                    text = "Deadlines",
                    style = MaterialTheme.typography.titleMedium
                )
                summary.deadlines.forEach { deadline ->
                    Text("• $deadline")
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Tags
            if (summary.tags.isNotEmpty()) {
                Text(
                    text = "Tags",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    summary.tags.forEach { tag ->
                        AssistChip(onClick = { }, label = { Text(tag) })
                    }
                }
            }
        }
    }
}
