package kt

import java.util.concurrent.ConcurrentLinkedDeque

object KotlinLogger {
    private val internalLogger: LoggerKt = LoggerKt(ConcurrentLinkedDeque(), 100)

    fun logger(): ILoggerKt {
        return internalLogger
    }
}
