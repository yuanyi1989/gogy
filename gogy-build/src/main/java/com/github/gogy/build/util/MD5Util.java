package com.github.gogy.build.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * @author yuanyi
 * @date 2018/1/23
 */
@Slf4j
public class MD5Util {

    private static int BUFFER_LENGTH = 1024;
    private static final char[] hexDigitsUC = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] hexDigitsLC = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    public static byte[] encodeToByte(File file) {

        try (FileInputStream inputStream = new FileInputStream(file)) {

            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            byte[] readBuff = new byte[BUFFER_LENGTH];

            int length = BUFFER_LENGTH;
            while ((length = inputStream.read(readBuff, 0, length)) != -1) {
                mdInst.update(readBuff, 0, length);
            }

            // 获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式

            return md;
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("md5crypt meet exception:%s, filePath:%s",
                    e.getMessage(), file.getPath()), e);
        }
    }

    public static String encodeToString(File file) {
        return byteToHexStr(encodeToByte(file));
    }

    /**
     * byte[]转16进制字符串(默认小写)
     * @param byteArray byte数组
     * @return 转换结果
     */
    public static String byteToHexStr(byte[] byteArray) {
        return byteToHexStr(true, byteArray);
    }

    /**
     * byte[]转16进制字符串
     * @param toLowerCase
     * @param digest
     * @return
     */
    public static String byteToHexStr(boolean toLowerCase, byte[] digest){
        //判断大小写
        char[] hexDigits = hexDigitsUC;
        if(toLowerCase){
            hexDigits = hexDigitsLC;
        }
        //转换成16进制
        int length = digest.length;
        char[] chars = new char[length * 2];
        int k = 0;
        for (int i = 0; i < length; i++) {
            byte byte0 = digest[i];
            chars[k++] = hexDigits[byte0 >>> 0x4 & 0xf];
            chars[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(chars);
    }

}
