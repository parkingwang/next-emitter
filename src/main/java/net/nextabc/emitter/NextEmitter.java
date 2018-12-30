package net.nextabc.emitter;

import net.nextabc.emitter.impl.MultiThreadsScheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class NextEmitter {

    private final Collection<Registration> mRegistrations = new CopyOnWriteArrayList<>();

    /**
     * 未捕获的异常处理接口
     */
    private Consumer<Throwable> mUncaughtExceptionHandler = Throwable::printStackTrace;

    /**
     * 调度器，默认实现为多线程调度器
     */
    private Scheduler mScheduler = new MultiThreadsScheduler();

    /**
     * 查找匹配的Handler，并执行调度
     */
    private Selector mSelector = new Selector() {

        @Override
        public void selectAndFire(VirtualKey key, Event event) {
            find(key).forEach(r -> {
                try {
                    r.handler.onEvent(event);
                } catch (Exception ex) {
                    try {
                        r.handler.onError(ex);
                    } catch (Exception ex1) {
                        mUncaughtExceptionHandler.accept(ex1);
                    }
                }
            });
        }

        private Collection<Registration> find(VirtualKey key) {
            final List<Registration> out = new ArrayList<>();
            for (Registration r : mRegistrations) {
                if (r.key.matches(key)) {
                    out.add(r);
                }
            }
            return out;
        }

    };

    /**
     * 设置未捕获异常处理接口。当 {@link EventHandler#onError(Exception)} 处理异常后依然抛出异常，将由此接口处理。
     *
     * @param handler 异常处理接口
     */
    public NextEmitter setUncaughtExceptionHandler(Consumer<Throwable> handler) {
        mUncaughtExceptionHandler = Objects.requireNonNull(handler, "uncaughtExceptionHandler == null");
        mScheduler.setUncaughtExceptionHandler(mUncaughtExceptionHandler);
        return this;
    }

    /**
     * 设置调度器
     *
     * @param scheduler 调度器
     * @return NextEmitter
     */
    public NextEmitter setScheduler(Scheduler scheduler) {
        mScheduler = Objects.requireNonNull(scheduler, "scheduler is null");
        return this;
    }

    /**
     * 设置Selector
     *
     * @param selector Selector
     * @return NextEmitter
     */
    public NextEmitter setSelector(Selector selector) {
        mSelector = Objects.requireNonNull(selector, "selector is null");
        return this;
    }

    /**
     * 注册 {@link EventHandler}以及它监听的 {@link VirtualKey}。
     * 当匹配的Key发生时， EventHandler 会被调度执行。
     *
     * @param key     监听的Key
     * @param handler 执行事件的Handler
     * @return NextEmitter
     */
    public NextEmitter registerHandler(VirtualKey key, EventHandler handler) {
        mRegistrations.add(new Registration(key, handler));
        return this;
    }

    /**
     * 移除 EventHandler
     *
     * @param handler EventHandler
     * @return NextEmitter
     */
    public NextEmitter removeHandler(EventHandler handler) {
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
    public NextEmitter emit(VirtualKey key, Event event) {
        mSelector.selectAndFire(key, event);
        return this;
    }

}
