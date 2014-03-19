/**
 *  Copyright 2010 Ryszard Wiśniewski <brut.alll@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package brut.util;

import brut.androlib.res.decoder.ARSCDecoder;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
abstract public class DataInputDelegate implements DataInput {
    protected final DataInput mDelegate;

    public DataInputDelegate(DataInput delegate) {
        this.mDelegate = delegate;
    }

    public int skipBytes(int n) throws IOException {
        byte[] b = new byte[n];
        mDelegate.readFully(b);
        ARSCDecoder.mOut.write(b);
//        return mDelegate.skipBytes(n);
        return n;
    }

    public int readUnsignedShort() throws IOException {
        int i = mDelegate.readUnsignedShort();
        ARSCDecoder.mOut.writeShort(i);
        return i;
    }

    public int readUnsignedByte() throws IOException {
        int i = mDelegate.readUnsignedByte();
        ARSCDecoder.mOut.writeByte(i);
        return i;
    }

    public String readUTF() throws IOException {
        String s = mDelegate.readUTF();
        ARSCDecoder.mOut.writeUTF(s);
        return s;
    }

    public short readShort() throws IOException {
        short i = mDelegate.readShort();
        ARSCDecoder.mOut.writeShort(i);
        return i;
    }

    public long readLong() throws IOException {
        long l = mDelegate.readLong();
        ARSCDecoder.mOut.writeLong(l);
        return l;
    }

    public String readLine() throws IOException {
        return mDelegate.readLine();
    }

    public int readInt() throws IOException {
        int i = mDelegate.readInt();
        ARSCDecoder.mOut.writeInt(i);
        return i;
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
        mDelegate.readFully(b, off, len);
        ARSCDecoder.mOut.write(b, off, len);
    }

    public void readFully(byte[] b) throws IOException {
        mDelegate.readFully(b);
        ARSCDecoder.mOut.write(b);
    }

    public float readFloat() throws IOException {
        float v = mDelegate.readFloat();
        ARSCDecoder.mOut.writeFloat(v);
        return v;
    }

    public double readDouble() throws IOException {
        double v = mDelegate.readDouble();
        ARSCDecoder.mOut.writeDouble(v);
        return v;
    }

    public char readChar() throws IOException {
        char c = mDelegate.readChar();
        ARSCDecoder.mOut.writeChar(c);
        return c;
    }

    public byte readByte() throws IOException {
        byte b = mDelegate.readByte();
        ARSCDecoder.mOut.writeByte(b);
        return b;
    }

    public boolean readBoolean() throws IOException {
        boolean b = mDelegate.readBoolean();
        ARSCDecoder.mOut.writeBoolean(b);
        return b;
    }
}
