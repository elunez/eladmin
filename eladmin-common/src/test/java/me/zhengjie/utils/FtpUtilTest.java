package me.zhengjie.utils;

import me.zhengjie.utils.ftp.FtpUtil;
import org.junit.jupiter.api.Test;

/**
 * @author pcshao.cn
 * @date 2025/3/12
 */
public class FtpUtilTest {

    @Test
    public void testDownload() throws Exception {
        String password = EncryptUtils.desDecrypt("xxx");
        FtpUtil.downloadFile("ftp.xxx.xx", 21, "ftptest", password,
                "/test.xlsx", "tempFile" + System.currentTimeMillis() + ".xlsx");
    }

    @Test
    public void testUpload() throws Exception {
        String password = EncryptUtils.desDecrypt("xxx");
        FtpUtil.uploadFile("ftp.xxx.xx", 21, "ftptest", password, "tempFile", "");
    }
}
