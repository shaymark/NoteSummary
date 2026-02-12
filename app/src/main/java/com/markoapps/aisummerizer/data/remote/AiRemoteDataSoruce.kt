package com.markoapps.aisummerizer.data.remote

import com.google.firebase.functions.FirebaseFunctions
import com.markoapps.aisummerizer.domain.model.SummaryMode
import kotlinx.coroutines.tasks.await

class OpenAiRemoteDataSource(
    private val functions: FirebaseFunctions = FirebaseFunctions.getInstance()
) {

    /**
     * Calls the Firebase Cloud Function "summarizeNote" to summarize a note.
     *
     * @param note the text of the note
     * @param mode the summary mode (e.g., "short", "detailed")
     * @return NoteSummary or null if failed
     */
    suspend fun summarize(note: String, mode: SummaryMode): String {
        return try {
            val data = hashMapOf(
                "note" to note,
                "mode" to mode.name
            )

            // Call the Firebase Function
            val result = functions
                .getHttpsCallable("summarizeNote")
                .call(data)
                .await()

            // response.data is the whole JSON object from Firebase
            val choices = (result.data as Map<*, *>)["choices"] as List<Map<String, Any>>
            val message = choices[0]["message"] as Map<String, Any>
            val content = message["content"] as String

            return content
        } catch (e: Exception) {
             e.printStackTrace()
            throw e
        }
    }
}