import java.io.File


fun readEnvFile(): Map<String, String> {
    val file = rootProject.file(".env")
    val map = mutableMapOf<String, String>()
    print("Reading .env file...")
    if (file.exists()) {
        file.readLines().forEach { line ->
            val trimmed = line.trim()
            if (trimmed.isNotEmpty() && !trimmed.startsWith("#") && trimmed.contains("=")) {
                val (key, value) = line.split("=", limit = 2)
                map[key] = value
            }
        }
    }
    print("map: $map")
    return map
}

// Expose via extra properties
extra["envVars"] = readEnvFile()