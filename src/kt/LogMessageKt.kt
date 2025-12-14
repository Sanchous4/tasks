package kt

import java.time.Instant


data class LogMessageKt(val message: String) {
    val timestamp: Instant = Instant.now()

    fun getFormattedTimestamp(): String = TimeFormatter.formatToUTC(timestamp)

    fun getFullMessage(): String = "${getFormattedTimestamp()} $message"
}
