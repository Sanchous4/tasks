package kt

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object TimeFormatter {
    fun formatToUTC(timestamp: Instant): String = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS 'UTC'")
        .withZone(ZoneOffset.UTC)
        .format(timestamp)
}
