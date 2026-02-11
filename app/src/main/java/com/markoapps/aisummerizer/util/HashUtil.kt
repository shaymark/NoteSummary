package com.markoapps.aisummerizer.util

import java.security.MessageDigest

object HashUtil {
    fun sha256(text: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(text.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}