package net.nextabc.emitter;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * Selector用于查找匹配事件Key的Handler，并调度执行。
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface Selector {

    /**
     * 绑定Context
     *
     * @param context Context
     */
    void select(@NotNull Context context);

    /**
     * 查找匹配事件Key的Handler，并调度执行。
     *
     * @param key   Key
     * @param event Event
     */
    <D> void fire(@NotNull VirtualKey key, @NotNull Event<D> event);

    /**
     * 清除资源
     */
    void destroy();

    /**
     * 等待Selector内部线程完成任务
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @throws InterruptedException 阻塞等待返回过程中被中断时，抛出异常
     */
    void awaitCompleted(long timeout, TimeUnit unit) throws InterruptedException;
}
