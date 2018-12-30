package net.nextabc.emitter;

/**
 * 事件处理器
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface EventHandler {

    /**
     * 处理事件
     *
     * @param event 事件对象
     */
    void onEvent(Event event) throws Exception;

    /**
     * 处理事件方法函数 {@link EventHandler#onError(Exception)} 发生异常时，可以抛出异常，并由此方法捕获处理。
     * 如果此方法仍然抛出异常，则由 {@link NextEmitter} 的 UncaughtExceptionHandler 处理。
     *
     * @param error 异常对象
     */
    void onError(Exception error) throws Exception;
}
