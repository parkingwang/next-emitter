package net.nextabc.emitter;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Context暴露一些 {@link NextEmitter} 内部组件访问方法和全局性质的工具函数。
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface Context {

    /**
     * 获取内部{@link VirtualKey}和{@link EventHandler}的注册信息。
     * 返回的集合是不可变对象，外部不可修改，否则会抛出异常。
     *
     * @return 注册信息列表，不可变集合。
     */
    @NotNull
    Collection<Registration> getRegistration();

    /**
     * 获取未处理异常的Handler
     *
     * @return 未处理异常的Handler
     */
    @NotNull
    Consumer<Throwable> getUncaughtExceptionHandler();

    /**
     * 返回Scheduler实现接口
     *
     * @return Scheduler实现接口
     */
    @NotNull
    Scheduler getScheduler();

    /**
     * 返回Scheduler实现接口
     *
     * @return Scheduler实现接口
     */
    @NotNull
    Selector getSelector();
}
