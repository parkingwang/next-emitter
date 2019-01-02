package net.nextabc.emitter;

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
    void select(Context context);

    /**
     * 查找匹配事件Key的Handler，并调度执行。
     *
     * @param key   Key
     * @param event Event
     */
    <D> void fire(VirtualKey key, Event<D> event);

    /**
     * 清除资源
     */
    void destroy();

    /**
     * 等待Selector内部线程完成任务
     */
    void awaitCompleted(long t, TimeUnit unit) throws InterruptedException;
}
