package net.nextabc.emitter;

import java.util.Objects;

/**
 * 事件对象
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class Event {

    public static final Object NOTHING = new Object();

    public final Object data1;
    public final Object data2;
    public final Object data3;

    private Event(Object data1, Object data2, Object data3) {
        this.data1 = Objects.requireNonNull(data1, "data1 == null");
        this.data2 = Objects.requireNonNull(data2, "data2 == null");
        this.data3 = Objects.requireNonNull(data3, "data3 == null");
    }

    public <T> T data() {
        return data1();
    }

    @SuppressWarnings("unchecked")
    public <T> T data1() {
        return (T) data1;
    }

    @SuppressWarnings("unchecked")
    public <T> T data2() {
        return (T) data2;
    }

    @SuppressWarnings("unchecked")
    public <T> T data3() {
        return (T) data3;
    }

    ////

    public static Event of(Object data1, Object data2, Object data3) {
        return new Event(data1, data2, data3);
    }

    public static Event of(Object data1, Object data2) {
        return new Event(data1, data2, NOTHING);
    }

    public static Event of(Object data1) {
        return new Event(data1, NOTHING, NOTHING);
    }
}
