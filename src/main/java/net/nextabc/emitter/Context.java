package net.nextabc.emitter;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Context暴露一些 {@link NextEmitter} 内部组件访问方法和全局性质的工具函数。
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface Context {

    Collection<Registration> getRegistration();

    Consumer<Throwable> getUncaughtExceptionHandler();

    Scheduler getScheduler();

    Selector getSelector();
}
