package com.markoapps.aisummerizer.util

class PriavacyRedactor {
}

fun redactSensitiveData(text: String): String {
    return text
        .replace(Regex("\\b\\d{9}\\b"), "[REDACTED_ID]")
        .replace(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"), "[REDACTED_EMAIL]")
        .replace(Regex("\\b05\\d[- ]?\\d{7}\\b"), "[REDACTED_PHONE]")
}
