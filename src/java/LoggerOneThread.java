package java;

import java.util.LinkedList;

public class LoggerOneThread extends Logger {
    public LoggerOneThread() {
        super(new LinkedList<>());
    }
}
