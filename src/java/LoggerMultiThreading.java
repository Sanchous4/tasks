package java;

import java.util.concurrent.ConcurrentLinkedDeque;

public class LoggerMultiThreading extends Logger {
    public LoggerMultiThreading() {
        super(new ConcurrentLinkedDeque<>());
    }
}
