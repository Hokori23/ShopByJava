package UTILS;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    /**
     * 计算字符串的hash值
     *
     * @param string    明文
     * @param algorithm 算法名
     * @return 字符串的hash值
     */
    public static String hash(String string, String algorithm) throws Exception {
        if (string.isEmpty()) {
            return "";
        }
        try {
            MessageDigest hash = MessageDigest.getInstance(algorithm);
            byte[] bytes = hash.digest(string.getBytes("UTF-8"));
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
    }
}