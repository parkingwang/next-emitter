package net.nextabc.emitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Default Selector
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class DefaultSelector implements Selector {

    private Context mContext;

    @Override
    public void select(Context context) {
        mContext = context;
    }

    @Override
    public <D> void fire(VirtualKey key, Event<D> event) {
        find(key).forEach(r -> {
            try {
                mContext.getScheduler().schedule(event, r.handler);
            } catch (Exception ex) {
                mContext.getUncaughtExceptionHandler().accept(ex);
            }
        });
    }

    @Override
    public void destroy() {
        // nop
    }

    @Override
    public void awaitCompleted(long timeout, TimeUnit unit) throws InterruptedException {
        // nop
    }

    private Collection<Registration> find(VirtualKey key) {
        final List<Registration> out = new ArrayList<>();
        for (Registration r : mContext.getRegistration()) {
            if (r.key.matches(key)) {
                out.add(r);
            }
        }
        return out;
    }
}
