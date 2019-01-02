package net.nextabc.emitter;

import java.util.concurrent.*;

/**
 * 使用一个线程和队列来接收外部输入事件，线程内部循环读取队列，并提交到{@link Scheduler}调度执行。
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class ThreadQueueSelector extends DefaultSelector {

    private final ExecutorService mLoopThread = new ThreadPoolExecutor(
            1, // core
            1,  // max
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    private final BlockingQueue<Element<?>> mQueue;

    public ThreadQueueSelector(int queueCapacity) {
        mQueue = new LinkedBlockingQueue<>(queueCapacity);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!mLoopThread.isShutdown()) {
                mLoopThread.shutdown();
            }
        }));
    }

    @Override
    public void select(Context context) {
        super.select(context);
        mLoopThread.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                final Element<?> ele;
                try {
                    ele = mQueue.take();
                } catch (InterruptedException e) {
                    break;
                }
                super.fire(ele.key, ele.event);
            }
        });
    }

    @Override
    public void destroy() {
        super.destroy();
        mLoopThread.shutdown();
    }

    @Override
    public void awaitCompleted(long timeout, TimeUnit unit) throws InterruptedException {
        super.awaitCompleted(timeout, unit);
        mLoopThread.awaitTermination(timeout, unit);
    }

    ////

    private static class Element<D> {

        final VirtualKey key;
        final Event<D> event;

        private Element(VirtualKey key, Event<D> event) {
            this.key = key;
            this.event = event;
        }
    }

}
