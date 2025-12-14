package kt

import java.util.Queue
import java.util.concurrent.atomic.AtomicInteger

interface ILoggerKt {
    fun log(message: String)
}

interface IInternalLoggerKt : ILoggerKt {
    fun drainLogs(logsNumberToPrint: Int)
    fun queueSize(): Int
}

class LoggerKt(
    logs: Queue<LogMessageKt>,
    val maxQueueSize: Int
) : IInternalLoggerKt {

    private val internalLogs: OptimizedQueue<LogMessageKt> = OptimizedQueue(logs)

    private val minLogsNumberToPrint = 10

    private fun getNumberOfLogs(logsNumberToPrint: Int): Int =
        if (logsNumberToPrint > minLogsNumberToPrint) logsNumberToPrint else minLogsNumberToPrint

    override fun queueSize(): Int = internalLogs.size()

    override fun drainLogs(logsNumberToPrint: Int) {
        val numberOfLogs = getNumberOfLogs(logsNumberToPrint)
        for (i in 0 until numberOfLogs) {
            val logToPrint = internalLogs.poll()

            if (logToPrint == null) break

            println(logToPrint.getFullMessage())
        }
    }

    override fun log(message: String) {
        if (queueSize() < maxQueueSize) {
            internalLogs.add(LogMessageKt(message))
            return
        }

        println("Queue is full!")
        internalLogs.removeAndAdd(LogMessageKt(message))
    }
}

class OptimizedQueue<T : Any>(val queue: Queue<T>) {
    private val size = AtomicInteger(0)

    fun poll(): T? {
        val polledElement = queue.poll()

        if (polledElement != null) size.decrementAndGet()

        return polledElement
    }

    fun add(element: T) {
        queue.add(element)
        size.incrementAndGet()
    }

    fun removeAndAdd(element: T) {
        poll()
        add(element)
    }

    fun size(): Int = size.get()
}

