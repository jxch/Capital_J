package org.jxch.capital.breath.a.utils;

import org.jetbrains.annotations.Nullable;

import java.io.UnsupportedEncodingException;

public class CharsetUtils {

    public static final String GB2312 = "GB2312";
    public static final String UTF_8 = "UTF-8";

    @Nullable
    public static String changeCharset(String str, String oldCharset, String newCharset) {
        try {
            if (str != null) {
                byte[] bs = str.getBytes(oldCharset);
                return new String(bs, newCharset);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


}
