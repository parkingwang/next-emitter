package net.nextabc.emitter;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可以设置线程ID的 {@link ThreadFactory}
 *
 * @author 陈永佳 (yoojiachen@gmail.com)
 * @version 0.0.1
 */
public class PrefixThreadFactory implements ThreadFactory {

    private final AtomicInteger mThreadId = new AtomicInteger(1);

    private final String mThreadIdPrefix;

    public PrefixThreadFactory(String threadIdPrefix) {
        mThreadIdPrefix = threadIdPrefix;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r);
        t.setName(mThreadIdPrefix + "#" + mThreadId.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
