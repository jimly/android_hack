package com.wujinliang;

import android.annotation.TargetApi;
import android.os.Build;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * weixin的AndroidManifest.xml修改包名以及provider名称
 * @author wujinliang
 * @since 2/19/14
 */
public class AndroidManifestModifyTest {

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void main(String[] args) throws Exception {
        // 先使用hexedit 编辑package
        FileInputStream is = new FileInputStream("/tmp/AndroidManifest.xml");
        byte[] data = IOUtils.toByteArray(is);
        Map<String, String> map = new HashMap<String, String>();
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...p.l.u.g.i.n...g.w.a.l.l.e.t...q.u.e.r.y.p.r.o.v.i.d.e.r", "c.o.m...t.e.n.c.e.n.t...m.n...p.l.u.g.i.n...g.w.a.l.l.e.t...q.u.e.r.y.p.r.o.v.i.d.e.r");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...s.d.k...p.l.u.g.i.n...p.r.o.v.i.d.e.r", "c.o.m...t.e.n.c.e.n.t...m.n...s.d.k...p.l.u.g.i.n...p.r.o.v.i.d.e.r");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...s.d.k...c.o.m.m...p.r.o.v.i.d.e.r", "c.o.m...t.e.n.c.e.n.t...m.n...s.d.k...c.o.m.m...p.r.o.v.i.d.e.r");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...p.l.u.g.i.n...e.x.t...E.x.t.C.o.n.t.e.n.t.P.r.o.v.i.d.e.r.B.a.s.e", "c.o.m...t.e.n.c.e.n.t...m.n...p.l.u.g.i.n...e.x.t...E.x.t.C.o.n.t.e.n.t.P.r.o.v.i.d.e.r.B.a.s.e");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...p.l.u.g.i.n...e.x.t...m.e.s.s.a.g.e", "c.o.m...t.e.n.c.e.n.t...m.n...p.l.u.g.i.n...e.x.t...m.e.s.s.a.g.e");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...p.l.u.g.i.n...e.x.t...S.e.a.r.c.h.C.o.n.t.a.c.t", "c.o.m...t.e.n.c.e.n.t...m.n...p.l.u.g.i.n...e.x.t...S.e.a.r.c.h.C.o.n.t.a.c.t");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...p.l.u.g.i.n...e.x.t...N.e.a.r.B.y", "c.o.m...t.e.n.c.e.n.t...m.n...p.l.u.g.i.n...e.x.t...N.e.a.r.B.y");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...p.l.u.g.i.n...e.x.t...S.N.S", "c.o.m...t.e.n.c.e.n.t...m.n...p.l.u.g.i.n...e.x.t...S.N.S");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...p.l.u.g.i.n...e.x.t...A.c.c.o.u.n.t.S.y.n.c", "c.o.m...t.e.n.c.e.n.t...m.n...p.l.u.g.i.n...e.x.t...A.c.c.o.u.n.t.S.y.n.c");
        map.put("c.o.m...t.e.n.c.e.n.t...m.m...p.l.u.g.i.n...e.x.t...e.n.t.r.y", "c.o.m...t.e.n.c.e.n.t...m.n...p.l.u.g.i.n...e.x.t...e.n.t.r.y");
        char first = 'c';
        ByteArrayOutputStream bios = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bios);
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            dos.writeByte(b);
            if ((char)b == first) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String from = entry.getKey();
                    String to = entry.getValue();
                    int len = from.length();
                    if (i + len < data.length - 1) {
                        boolean found = true;
                        for (int j = 1; j < len; j++) {
                            if (from.charAt(j) != '.' && from.charAt(j) != (char)data[i + j]) {
                                found = false;
                                break;
                            }
                        }
                        if (found) {
                            for (int j = 1; j < len; j++) {
                                dos.writeByte((int) (to.charAt(j)));
                            }
                            i += (len - 1);
                            break;
                        }
                    }
                }
            }
        }
        IOUtils.write(bios.toByteArray(), new FileOutputStream("/tmp/weixin.xml"));
    }
}
