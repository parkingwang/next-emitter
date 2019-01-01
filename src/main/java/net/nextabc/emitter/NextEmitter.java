package net.nextabc.emitter;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class NextEmitter implements Context {

    private final Collection<Registration> mRegistrations = new CopyOnWriteArrayList<>();

    /**
     * 未捕获的异常处理接口。
     * 当 {@link EventHandler#onError(Exception)} 处理异常后依然抛出异常，将由此接口处理。
     */
    private final Consumer<Throwable> mUncaughtExceptionHandler;

    /**
     * 调度器，默认实现为多线程调度器
     */
    private final Scheduler mScheduler;

    /**
     * 查找匹配的Handler，并执行调度
     */
    private final Selector mSelector;

    private NextEmitter(Consumer<Throwable> handler, Scheduler scheduler, Selector selector) {
        mUncaughtExceptionHandler = handler;
        mScheduler = scheduler;
        mSelector = selector;
        //
        mScheduler.setUncaughtExceptionHandler(mUncaughtExceptionHandler);
        mSelector.select(this);
    }

    /**
     * 注册 {@link EventHandler}以及它监听的 {@link VirtualKey}。
     * 当匹配的Key发生时， EventHandler 会被调度执行。
     *
     * @param key     监听的Key
     * @param handler 执行事件的Handler
     * @return NextEmitter
     */
    public <D> NextEmitter registerHandler(VirtualKey key, EventHandler<D> handler) {
        mRegistrations.add(new Registration(key, handler));
        return this;
    }

    /**
     * 移除 EventHandler
     *
     * @param handler EventHandler
     * @return NextEmitter
     */
    public NextEmitter removeHandler(EventHandler<?> handler) {
        mRegistrations.removeIf(it -> it.handler == handler);
        return this;
    }

    /**
     * 提交事件及其所属 {@link VirtualKey}。
     * 已注册的 EventHandler ，如果Key匹配，会被调度执行。
     *
     * @param key   事件所属Key
     * @param event 事件对象
     * @return NextEmitter
     */
    public <D> NextEmitter emit(VirtualKey key, Event<D> event) {
        mSelector.fire(key, event);
        return this;
    }

    @Override
    public Collection<Registration> getRegistration() {
        return mRegistrations;
    }

    @Override
    public Consumer<Throwable> getUncaughtExceptionHandler() {
        return mUncaughtExceptionHandler;
    }

    @Override
    public Scheduler getScheduler() {
        return mScheduler;
    }

    @Override
    public Selector getSelector() {
        return mSelector;
    }

    ////

    public static Builder newBuilder() {
        return new Builder();
    }

    ////

    /**
     * Builder用于创建 {@link NextEmitter}
     */
    public static class Builder {

        private Consumer<Throwable> mUncaughtExceptionHandler;
        private Scheduler mScheduler;
        private Selector mSelector;

        /**
         * 创建一个默认功能实现的{@link NextEmitter}对象。
         *
         * @return NextEmitter对象
         */
        public NextEmitter buildDefault() {
            setUncaughtExceptionHandler(Throwable::printStackTrace);
            setScheduler(new MultiThreadsScheduler());
            setSelector(new DefaultSelector());
            return build();
        }

        /**
         * 创建一个队列缓存的Selector实现，返回创建的 NextEmitter 对象。
         *
         * @param queueCapacity 队列缓存容量
         * @return NextEmitter对象
         */
        public NextEmitter buildWithQueueSelector(int queueCapacity) {
            setUncaughtExceptionHandler(Throwable::printStackTrace);
            setScheduler(new MultiThreadsScheduler());
            setSelector(new ThreadQueueSelector(queueCapacity));
            return build();
        }

        public NextEmitter build() {
            return new NextEmitter(
                    Objects.requireNonNull(mUncaughtExceptionHandler, "uncaughtExceptionHandler == null"),
                    Objects.requireNonNull(mScheduler, "scheduler is null"),
                    Objects.requireNonNull(mSelector, "selector is null")
            );
        }

        public Builder setUncaughtExceptionHandler(Consumer<Throwable> handler) {
            mUncaughtExceptionHandler = handler;
            return this;
        }

        public Builder setScheduler(Scheduler scheduler) {
            mScheduler = scheduler;
            return this;
        }

        public Builder setSelector(Selector selector) {
            mSelector = selector;
            return this;
        }
    }
}
