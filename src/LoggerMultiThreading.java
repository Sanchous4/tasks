import java.util.concurrent.ConcurrentLinkedDeque;

public class LoggerMultiThreading extends Logger {
    LoggerMultiThreading() {
        super(new ConcurrentLinkedDeque<>());
    }
}
