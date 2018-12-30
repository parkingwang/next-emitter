package net.nextabc.emitter.impl;

import net.nextabc.emitter.Event;
import net.nextabc.emitter.EventHandler;
import net.nextabc.emitter.Scheduler;

import java.util.concurrent.*;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class MultiThreadsScheduler implements Scheduler {

    private final ExecutorService mThread = new ThreadPoolExecutor(
            2,
            Runtime.getRuntime().availableProcessors() * 2,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>()
    );

    public MultiThreadsScheduler() {
        Runtime.getRuntime().addShutdownHook(new Thread(mThread::shutdown));
    }

    @Override
    public void schedule(Event event, EventHandler handler) {
        mThread.submit(() -> {
            try {
                handler.onEvent(event);
            } catch (Exception ex) {
                handler.onError(ex);
            }
        });
    }
}
