package me.zhengjie.core.utils;

import org.springframework.util.DigestUtils;

/**
 * 加密
 * @author jie
 * @date 2018-11-23
 */
public class EncryptUtils {

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        return  DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("e10adc3949ba59abbe56e057f20f883e"));
    }
}
