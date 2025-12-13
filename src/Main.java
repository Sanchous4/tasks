import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        testOneTreadLogger();
        testMultiThreadingLogger();
    }

    public static void testOneTreadLogger() {
        val loggerOneThread = new LoggerOneThread();
        loggerOneThread.log("Hello World!");
        loggerOneThread.printNextLog();
    }

    public static String threadMessage(final @NotNull String message) {
        return message+"with id %s!".formatted(Thread.currentThread().threadId());
    }

    private static final ScheduledExecutorService logPrinter = Executors.newSingleThreadScheduledExecutor();

    public static void testMultiThreadingLogger() {
        val loggerMultiThreading = new LoggerMultiThreading();

        Thread.startVirtualThread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                loggerMultiThreading.log(Main.threadMessage("I am first thread!"));
                try {
                    sleep(Duration.ofSeconds(1));
                } catch (InterruptedException e) {
                    Main.threadMessage("I am was interrupted!");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        Thread.startVirtualThread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                loggerMultiThreading.log(Main.threadMessage("I am second thread!"));
                try {
                    sleep(Duration.ofSeconds(1));
                } catch (InterruptedException e) {
                    Main.threadMessage("I am was interrupted!");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        logPrinter.execute(() -> loggerMultiThreading.setThreadId(Thread.currentThread().threadId()));
        logPrinter.scheduleWithFixedDelay(loggerMultiThreading::printNextLog, 500,
                500,
                TimeUnit.MILLISECONDS);
    }
}

