package me.zhengjie.utils;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static me.zhengjie.utils.FileUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilTest {

    @Test
    public void testToFile() {
        byte[] content = "eladmin".getBytes(StandardCharsets.UTF_8);
        File file = toFile(new MockMultipartFile("foo", "foo.txt", "text/plain", content));
        assertNotNull(file);
        assertTrue(file.exists(), "临时文件未生成: " + file);
        // 文件名随机，但后缀取自原始文件名
        assertTrue(file.getName().endsWith(".txt"), "后缀丢失: " + file.getName());
        assertEquals(content.length, file.length());
        assertTrue(file.delete(), "临时文件清理失败: " + file);
    }

    @Test
    public void testGetExtensionName() {
        assertEquals("foo", getExtensionName("foo"));
        assertEquals("exe", getExtensionName("bar.exe"));
    }

    @Test
    public void testGetFileNameNoEx() {
        assertEquals("foo", getFileNameNoEx("foo"));
        assertEquals("bar", getFileNameNoEx("bar.txt"));
    }

    @Test
    public void testGetSize() {
        assertEquals("1000B", getSize(1000));
        assertEquals("1.00KB", getSize(1024));
        assertEquals("1.00MB", getSize(1048576));
        assertEquals("1.00GB", getSize(1073741824));
    }
}
