package com.markoapps.aisummerizer.data.remote.model

data class ChatCompletionResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val index: Int,
    val message: ChatMessageResponse
)

data class ChatMessageResponse(
    val role: String,
    val content: String
)