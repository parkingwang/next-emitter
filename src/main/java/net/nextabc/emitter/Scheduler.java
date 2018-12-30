package net.nextabc.emitter;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface Scheduler {

    void schedule(Event event, EventHandler handler) throws Exception;
}
