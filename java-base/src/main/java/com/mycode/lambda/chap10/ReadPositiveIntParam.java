package com.mycode.lambda.chap10;

import org.junit.Test;

import javax.naming.Name;
import javax.swing.text.rtf.RTFEditorKit;
import java.security.PublicKey;
import java.util.Optional;
import java.util.Properties;

/**
 * jf
 * 2018/10/14 18:20
 */
public class ReadPositiveIntParam {
    @Test
    public void testMap() {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");
    }

    public static int readDurationImperative(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException nfe) {
            }
        }
        return 0;
    }

    public static int readDurationWithOptional(Properties properties, String name) {
        return  Optional.ofNullable(properties.getProperty(name))
                .flatMap(ReadPositiveIntParam::s2i)
                .filter(i -> i > 0)
                .orElse(0);
    }

    public static Optional<Integer> s2i(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
