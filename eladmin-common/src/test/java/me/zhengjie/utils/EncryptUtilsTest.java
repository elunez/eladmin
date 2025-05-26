package me.zhengjie.utils;

import org.junit.jupiter.api.Test;

import static me.zhengjie.utils.EncryptUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncryptUtilsTest {

    /**
     * Symmetric encryption
     */
    @Test
    public void testDesEncrypt() {
        try {
            assertEquals("7772841DC6099402", desEncrypt("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Symmetric decryption
     */
    @Test
    public void testDesDecrypt() {
        try {
            assertEquals("123456", desDecrypt("7772841DC6099402"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
