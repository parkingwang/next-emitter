package net.nextabc.emitter;

import java.util.Objects;

/**
 * 事件对象。提供三个额外的数据字段。
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public class Event<D> {

    public static final Object NOTHING = new Object();

    public final D data;
    public final Object data1;
    public final Object data2;
    public final Object data3;

    private Event(D data, Object data1, Object data2, Object data3) {
        this.data = Objects.requireNonNull(data, "data == null");
        this.data1 = Objects.requireNonNull(data1, "data1 == null");
        this.data2 = Objects.requireNonNull(data2, "data2 == null");
        this.data3 = Objects.requireNonNull(data3, "data3 == null");
    }

    /**
     * 获取数据字段
     *
     * @return data字段数据
     */
    public D data() {
        return data;
    }

    /**
     * 获取data1字段数据，并强制转换成接收类型。
     *
     * @param <T> 接收类型
     * @return data1数据
     */
    @SuppressWarnings("unchecked")
    public <T> T data1() {
        return (T) data1;
    }

    /**
     * 获取data2字段数据，并强制转换成接收类型。
     *
     * @param <T> 接收类型
     * @return data2数据
     */
    @SuppressWarnings("unchecked")
    public <T> T data2() {
        return (T) data2;
    }

    /**
     * 获取data3字段数据，并强制转换成接收类型。
     *
     * @param <T> 接收类型
     * @return data3数据
     */
    @SuppressWarnings("unchecked")
    public <T> T data3() {
        return (T) data3;
    }

    ////

    public static <D> Event<D> of(D data) {
        return of(data, NOTHING, NOTHING, NOTHING);
    }

    public static <D> Event<D> of(D data, Object data1) {
        return of(data, data1, NOTHING, NOTHING);
    }

    public static <D> Event<D> of(D data, Object data1, Object data2) {
        return of(data, data1, data2, NOTHING);
    }

    public static <D> Event<D> of(D data, Object data1, Object data2, Object data3) {
        return new Event<>(data, data1, data2, data3);
    }

}
