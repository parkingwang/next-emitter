package net.nextabc.emitter;

/**
 * 事件处理器
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface EventHandler {

    void onEvent(Event event);

    void onError(Exception error);
}
