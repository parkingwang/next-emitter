package net.nextabc.emitter;

import java.util.function.Consumer;

/**
 * Scheduler调度器，用于调度执行事件处理接口 {@link EventHandler}
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface Scheduler {

    /**
     * 未捕获函数接口
     *
     * @param handler Handler
     */
    void setUncaughtExceptionHandler(Consumer<Throwable> handler);

    /**
     * 调度事件来执行
     *
     * @param event   事件
     * @param handler Handler
     * @throws Exception 发生错误
     */
    void schedule(Event event, EventHandler handler) throws Exception;
}
