package net.nextabc.emitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 多线程调度器，使用 {@link ExecutorService} 来执行线程
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class MultiThreadsScheduler implements Scheduler {

    private final ExecutorService mThreads;

    private Consumer<Throwable> mUncaughtExceptionHandler;

    public MultiThreadsScheduler(int coreThreads, int maxThreads) {
        mThreads = new ThreadPoolExecutor(
                coreThreads,
                maxThreads,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>()
        );
        Runtime.getRuntime().addShutdownHook(new Thread(mThreads::shutdown));
    }

    public MultiThreadsScheduler() {
        this(4, Runtime.getRuntime().availableProcessors() * 2);
    }

    @Override
    public void setUncaughtExceptionHandler(Consumer<Throwable> handler) {
        mUncaughtExceptionHandler = handler;
    }

    @Override
    public <D> void schedule(Event<D> event, EventHandler<D> handler) {
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
