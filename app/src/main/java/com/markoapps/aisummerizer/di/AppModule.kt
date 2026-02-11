package com.markoapps.aisummerizer.di

import androidx.room.Room
import com.markoapps.aisummerizer.data.local.AiLocalDataSource
import com.markoapps.aisummerizer.data.local.JsonListConverter
import com.markoapps.aisummerizer.data.local.db.AppDatabase
import com.markoapps.aisummerizer.data.local.mapper.SummaryMapper
import com.markoapps.aisummerizer.data.remote.OpenAiRemoteDataSource
import com.markoapps.aisummerizer.data.remote.parser.SummaryParser
import com.markoapps.aisummerizer.data.repository.AiRepository
import com.markoapps.aisummerizer.domain.usecase.SummarizeNoteUseCase
import com.markoapps.aisummerizer.viewmodel.HistoryViewModel
import com.markoapps.aisummerizer.viewmodel.NoteViewModel
import com.markoapps.aisummerizer.viewmodel.SummaryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "ai_summarizer_db"
        ).build()
    }

    single { get<AppDatabase>().summaryDao() }

    // Data sources
    single { AiLocalDataSource(get(), get()) }
    single { OpenAiRemoteDataSource(get()) }

    // Mapper / Parser
    single { SummaryMapper(get()) }
    single { JsonListConverter() }
    single { SummaryParser() }

    // Repository
    single { AiRepository(get(), get(), get()) }

    // UseCase
    factory { SummarizeNoteUseCase(get()) }

    // ViewModels
    viewModel { NoteViewModel(get()) }
    viewModel { HistoryViewModel(get()) }

    // Dedicated SummaryViewModel with noteId
    viewModel { (noteId: String) -> SummaryViewModel(noteId, get()) }
}