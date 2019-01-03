package net.nextabc.emitter;

import org.jetbrains.annotations.NotNull;

/**
 * 事件处理器
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface EventHandler<D> {

    /**
     * 处理事件
     *
     * @param event 事件对象
     * @throws Exception 处理事件发生错误时抛出异常
     */
    void onEvent(@NotNull Event<D> event) throws Exception;

    /**
     * 处理事件方法函数 {@link EventHandler#onError(Exception)} 发生异常时，可以抛出异常，并由此方法捕获处理。
     * 如果此方法仍然抛出异常，则由 {@link NextEmitter} 的 UncaughtExceptionHandler 处理。
     *
     * @param error 异常对象
     * @throws Exception 无法处理的错误，继续抛出异常。
     */
    void onError(@NotNull Exception error) throws Exception;
}
