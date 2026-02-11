import java.util.Properties
import java.io.File

// Function to load .env file
fun loadEnv(): Map<String, String> {
    val envFile = rootProject.file(".env")
    val map = mutableMapOf<String, String>()
    if (envFile.exists()) {
        envFile.readLines(Charsets.UTF_8).forEach { line ->
            val trimmed = line.trim()
            if (trimmed.isNotEmpty() && !trimmed.startsWith("#") && trimmed.contains("=")) {
                val (key, value) = trimmed.split("=", limit = 2)
                map[key] = value
            }
        }
    }
    return map
}

val env = loadEnv()


android {
    defaultConfig {
        // Add .env variables to BuildConfig
        buildConfigField("String", "OPENAI_API_KEY", "\"${env["MY_API_KEY"] ?: "default"}\"")
    }
}