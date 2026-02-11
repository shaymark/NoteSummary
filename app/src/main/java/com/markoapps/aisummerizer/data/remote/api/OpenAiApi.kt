package com.markoapps.aisummerizer.data.remote.api

import com.markoapps.aisummerizer.data.remote.model.ChatCompletionRequest
import com.markoapps.aisummerizer.data.remote.model.ChatCompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {

    @POST("v1/chat/completions")
    suspend fun createChatCompletion(
        @Body request: ChatCompletionRequest
    ): ChatCompletionResponse
}