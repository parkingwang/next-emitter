package net.nextabc.emitter;

import java.util.Objects;

/**
 * VirtualKey是表示一个Key的接口。
 *
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface VirtualKey {

    /**
     * 返回Key对象。
     *
     * @return Key对象
     */
    <T> T getKey();

    /**
     * 比较两个Key是否匹配
     *
     * @param key 其它Key
     * @return 是否匹配
     */
    boolean matches(VirtualKey key);

    ////

    /**
     * 字符串Key
     */
    class String implements VirtualKey {

        private final java.lang.String mKey;

        public String(java.lang.String key) {
            mKey = Objects.requireNonNull(key, "key == null");
        }

        public static VirtualKey of(java.lang.String key) {
            return new String(key);
        }

        @Override
        @SuppressWarnings("unchecked")
        public java.lang.String getKey() {
            return mKey;
        }

        @Override
        public boolean matches(VirtualKey key) {
            return getKey().equals(key.getKey());
        }
    }
}
