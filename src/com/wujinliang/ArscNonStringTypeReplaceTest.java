package com.wujinliang;

import brut.androlib.res.data.ResTable;
import brut.androlib.res.decoder.ARSCDecoder;
import com.wujinliang.arsc.LittleEndianDataOutputStream;
import com.wujinliang.arsc.RemovableByteArrayOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>android resources.arsc文件替换，解决ArscEditor不能替换非字符串的问题</p>
 * <p>ArscEditor下载地址：http://pan.baidu.com/s/1jGxBoKe </p>
 *
 * @author wujinliang
 * @since 3/19/14
 */
public class ArscNonStringTypeReplaceTest {
    private static Map<String, String> newColorMap = new HashMap<String, String>();
    static {
        newColorMap.put("word_bar", "0x123456");// word_bar是你在原来resources.arsc里面存在的color key
    }

    public static void main(String[] args) {
        RemovableByteArrayOutputStream bos = new RemovableByteArrayOutputStream();
        ARSCDecoder.mOut = new LittleEndianDataOutputStream(bos);
        FileInputStream is = null;
        try {
            is = new FileInputStream("/tmp/origin/resources.arsc");
            ARSCDecoder.newColorMap = newColorMap;
            ARSCDecoder.decode(is, false, true, new ResTable());

            File file = new File("/tmp/dest/resources.arsc");
            if (file.exists()) {
                file.delete();
            }
            FileUtils.writeByteArrayToFile(file, bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}
