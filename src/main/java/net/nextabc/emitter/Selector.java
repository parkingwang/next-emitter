package net.nextabc.emitter;

import java.util.Collection;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com, chenyongjia@parkingwang.com)
 * @version 0.1
 */
public interface Selector {

    Collection<Registration> select(VirtualKey key);
}
