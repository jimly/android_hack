package com.wujinliang.arsc;

import java.io.ByteArrayOutputStream;

/**
 * @author wujinliang
 * @since 3/19/14
 */
public class RemovableByteArrayOutputStream extends ByteArrayOutputStream {
    /**
     * 删除指定个byte字节
     * @param num byte字节个数
     */
    public void remove(int num) {
        count = count - num;
    }
}
