package org.mendora.util.generate;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by kam on 2018/4/13.
 */
public class IDUtils {
    // salt字符集
    private static final String[] RANDOM_CODE = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };


    public static String salt(int len) {
        StringBuilder sb = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < len; i++) {
            int count = secureRandom.nextInt(RANDOM_CODE.length);
            sb.append(RANDOM_CODE[count]);
        }
        return sb.toString();
    }


    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
