package net.nextabc.emitter.impl;

import net.nextabc.emitter.Event;
import net.nextabc.emitter.EventHandler;
import net.nextabc.emitter.Scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class MultiThreadsScheduler implements Scheduler {

    private final ExecutorService mThreads = new ThreadPoolExecutor(
            2,
            Runtime.getRuntime().availableProcessors() * 2,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>()
    );

    private Consumer<Throwable> mUncaughtExceptionHandler;

    public MultiThreadsScheduler() {
        Runtime.getRuntime().addShutdownHook(new Thread(mThreads::shutdown));
    }

    @Override
    public void setUncaughtExceptionHandler(Consumer<Throwable> handler) {
        mUncaughtExceptionHandler = handler;
    }

    @Override
    public void schedule(Event event, EventHandler handler) {
        mThreads.submit(() -> {
            try {
                handler.onEvent(event);
            } catch (Exception ex) {
                try {
                    handler.onError(ex);
                } catch (Exception ex2) {
                    mUncaughtExceptionHandler.accept(ex2);
                }
            }
        });
    }
}
