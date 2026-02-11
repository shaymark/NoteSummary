package com.markoapps.aisummerizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.markoapps.aisummerizer.ui.screen.HistoryScreenRoute
import com.markoapps.aisummerizer.ui.screen.NoteScreenRoute
import com.markoapps.aisummerizer.ui.screen.SummeryScreenRoute
import com.markoapps.aisummerizer.viewmodel.HistoryViewModel
import com.markoapps.aisummerizer.viewmodel.NoteViewModel
import com.markoapps.aisummerizer.viewmodel.SummaryViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "note"
    ) {
        // 1️⃣ NoteScreen
        composable("note") {
            val viewModel: NoteViewModel = koinViewModel()
            NoteScreenRoute(
                viewModel = koinViewModel()
            )
        }

        // 2️⃣ SummaryScreen
        composable(
            route = "summary/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable

            val summaryViewModel: SummaryViewModel = koinViewModel { parametersOf(noteId) }

            SummeryScreenRoute(
                viewModel = summaryViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // 3️⃣ HistoryScreen
        composable("history") {
            val historyViewModel: HistoryViewModel = koinViewModel()
            HistoryScreenRoute(
                viewModel = historyViewModel,
                onItemClick = { summary ->
                    navController.navigate("summary/${summary.id}")
                }
            )
        }
    }
}