package kt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay

object LogConsumer {
    val loggerScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    suspend fun runLogger() {
        val logger = KotlinLogger.logger()

        if (logger is IInternalLoggerKt) {
            loggerScope.run {
                delay(1000)
                logger.drainLogs(
                    logger.queueSize()
                )
            }
            return
        }

        println("Logger is not an instance of IInternalLoggerKt!")
    }
}
