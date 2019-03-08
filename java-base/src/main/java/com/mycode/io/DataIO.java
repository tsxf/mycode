package com.mycode.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 蛮小江
 * 2018/9/13 11:31
 */
public class DataIO {
    public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length()) {
                ch = s.charAt(i);
            }
            out.writeChar(ch);
        }
    }

    public static String readFixedString(int size, DataInput in) throws IOException {
        boolean more = true;
        StringBuilder sb = new StringBuilder(size);
        int i = 0;
        while (more && i < size) {
            char ch = in.readChar();
            i++;
            if (ch == 0) {
                more = false;
            } else {
                sb.append(ch);
            }
        }
        //一个字符占两个字节
        in.skipBytes(2 * (size - i));
        return sb.toString();
    }
}
