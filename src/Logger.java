import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.Objects;

public abstract class Logger {
    private final int MAX_QUEUE_SIZE;
    private volatile long threadId = Thread.currentThread().threadId();

    protected final Deque<LogMessage> queue;

    protected Logger(Deque<LogMessage> queue) {
        this.queue = queue;
        this.MAX_QUEUE_SIZE = 10;
    }

    public void setThreadId(long threadId) {
        synchronized (this) {
            this.threadId = threadId;
        }
    }

    protected boolean compareThreadId() {
        return Thread.currentThread().threadId() == threadId;
    }

    public void log(final @Nullable String message) {
        if(Objects.isNull(message)) return;

        if(queue.size() >= MAX_QUEUE_SIZE) {
            queue.removeFirst();
            System.out.println("The queue is full! The oldest log was removed!");
        }

        val newLog = LogMessage.builder().message(message).build();
        queue.add(newLog);
    }

    protected String formatLog(final @NotNull LogMessage log) {
        return "%s at %s".formatted(log.getMessage(), log.getTimestamp());
    }

    public void printNextLog() {
        if(!compareThreadId()) {
            System.out.println("The thread can be consumer, override thread id by setThreadId()!");
            return;
        }

        val log = queue.poll();

        if(Objects.isNull(log)) {
            System.out.println("No logs!");
            return;
        }

        val message = this.formatLog(log);
        System.out.println(message);
    }
}
