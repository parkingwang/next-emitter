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
    class TextKey implements VirtualKey {

        private final String mKey;

        public TextKey(String key) {
            mKey = Objects.requireNonNull(key, "key == null");
        }

        public static VirtualKey of(String key) {
            return new TextKey(key);
        }

        @Override
        @SuppressWarnings("unchecked")
        public String getKey() {
            return mKey;
        }

        @Override
        public boolean matches(VirtualKey key) {
            return getKey().equals(key.getKey());
        }
    }
}
