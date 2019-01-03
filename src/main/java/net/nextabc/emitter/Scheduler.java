package net.nextabc.emitter;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
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
    void setUncaughtExceptionHandler(@NotNull Consumer<Throwable> handler);

    /**
     * 调度事件来执行
     *
     * @param event   事件
     * @param handler Handler
     * @throws Exception 发生错误
     */
    <D> void schedule(@NotNull Event<D> event, @NotNull EventHandler<D> handler) throws Exception;

    /**
     * 清除资源
     */
    void destroy();

    /**
     * 等待Scheduler内部线程完成任务
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @throws InterruptedException 阻塞等待返回过程中被中断时，抛出异常
     */
    void awaitCompleted(long timeout, TimeUnit unit) throws InterruptedException;
}
