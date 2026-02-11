package com.markoapps.aisummerizer.data.remote

import com.markoapps.aisummerizer.data.remote.api.OpenAiApi
import com.markoapps.aisummerizer.data.remote.model.ChatCompletionRequest
import com.markoapps.aisummerizer.data.remote.model.ChatMessage
import com.markoapps.aisummerizer.domain.model.SummaryMode

class OpenAiRemoteDataSource(
    private val api: OpenAiApi
) {

    suspend fun summarize(note: String, mode: SummaryMode): String {
        val systemPrompt =
            "You are a note summarizer. Return ONLY valid JSON."

        val userPrompt =
            """
            Summarize the note in mode: $mode
            Summarize the following note into JSON:
            {
              "tldr": "...",
              "summary": "...",
              "actionItems": ["..."],
              "deadlines": ["..."],
              "tags": ["..."]
            }

            Note:
            <<<
            $note
            >>>
            """.trimIndent()

        val request = ChatCompletionRequest(
            model = "gpt-4o-mini",
            messages = listOf(
                ChatMessage("system", systemPrompt),
                ChatMessage("user", userPrompt)
            )
        )

        val response = api.createChatCompletion(request)

        return response.choices.first().message.content
    }
}