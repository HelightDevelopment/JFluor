package dev.helight.jfluor;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JFluor {

    private static final Map<String, JFluor> loggerRegistry = new ConcurrentHashMap<>();

    @Getter
    private final String name;

    @Getter
    private final Logger nativeLogger;

    @Getter
    private final LogWorker logWorker = this.new LogWorker();

    @Getter @Setter
    private Level level = Level.INFO;

    @Getter
    private BlockingQueue<FMSG.MessageSnapshot> messageQueue = new ArrayBlockingQueue(1024);

    public JFluor(String name) {
        this.name = name;
        this.nativeLogger = Logger.getLogger(name);

        logWorker.start();
    }

    private void print(FMSG.MessageSnapshot snapshot) {
        //nativeLogger.log(new LogRecord(level, snapshot.getStringBuffer().toString()));
        String s = snapshot.stringBuffer.toString();
        System.out.print(s);
    }

    class LogWorker extends Thread {

        @Override
        @SneakyThrows
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                FMSG.MessageSnapshot snapshot = messageQueue.take();
                print(snapshot);
                Thread.sleep(100);
            }
        }
    }

}
