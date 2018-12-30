package net.nextabc.emitter;

import java.util.function.Consumer;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface Scheduler {

    void setUncaughtExceptionHandler(Consumer<Throwable> handler);

    void schedule(Event event, EventHandler handler) throws Exception;
}
