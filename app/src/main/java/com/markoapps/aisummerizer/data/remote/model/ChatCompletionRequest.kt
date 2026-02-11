package com.markoapps.aisummerizer.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Double = 0.2
)

@Serializable
data class ChatMessage(
    val role: String,   // "system" / "user"
    val content: String
)