package cn.lyb.zc.utils;

import cn.lyb.zc.common.Constant;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lyb
 * @since 2019/8/8 14:08
 */
public class Md5Util {

    public static String md5(String source) {
        if (StringUtils.isEmpty(source)) {
            throw new RuntimeException(Constant.MESSAGE_CODE_INVALID);
        }
        StringBuilder builder = new StringBuilder();
        // 准备字符数组
        char[] characters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        // 指定加密算法
        String algorithm = "MD5";

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            // 将要加密的明文转换成字节数组形式
            byte[] inputBytes = source.getBytes();

            // 执行加密
            byte[] outputBytes = digest.digest(inputBytes);
            for (byte b : outputBytes) {
                // 获取低四位值
                int lowValue = b & 15;

                // 右移四位和15做与运算得到高四位值
                int highValue = (b >> 4) & 15;

                // 以高四位、低四位的值为下标从字符数组中获取对应字符
                char highCharacter = characters[highValue];
                char lowCharacter = characters[lowValue];

                // 拼接
                builder.append(highCharacter).append(lowCharacter);

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return builder.toString();

    }
}
