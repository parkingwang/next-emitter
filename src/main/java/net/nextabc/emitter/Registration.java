package net.nextabc.emitter;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public final class Registration {

    public final VirtualKey key;
    public final EventHandler handler;

    <D> Registration(VirtualKey key, EventHandler<D> handler) {
        this.key = key;
        this.handler = handler;
    }
}
