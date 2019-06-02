package me.zhengjie.utils;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.BadRequestException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * File工具类，扩展 hutool 工具包
 *
 * @author jie
 * @date 2018-12-27
 */
@Slf4j
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 定义GB的计算常量
     */
    private static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;

    /**
     * 格式化小数
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * MultipartFile转File
     *
     * @param multipartFile
     * @return
     */
    public static File toFile(MultipartFile multipartFile) {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = "." + getExtensionName(fileName);
        File file = null;
        try {
            // 用uuid作为文件名，防止生成的临时文件重复
            file = File.createTempFile(IdUtil.simpleUUID(), prefix);
            // MultipartFile to File
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void main(String args[]) {
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "test.png";
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        FileUtil.toFile(multipartFile, "/files/**", "/Users/wuyang/Downloads/xpay/");
    }

    public static String toFile(MultipartFile multipartFile, String staticPath, String ctxDir) {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        String autoCreatedDateDir = DateFormatUtils.format(new Date(), "yyyy/MMdd/");
        File savePath = new File(ctxDir + autoCreatedDateDir);
        log.info("===> savePathLocal path is: {}", savePath);
        if (!savePath.exists()) {
            if (!savePath.mkdirs()) {
                throw new BadRequestException("创建文件目录失败");
            }
        }
        String fileSaveName = StringUtils.join(IdUtil.simpleUUID(), ".", getExtensionName(fileName));
        String fileSavePath = ctxDir + autoCreatedDateDir + fileSaveName;
        String fileSavePathNoCtx = autoCreatedDateDir + fileSaveName;
        String fileServerPath = null;
        try {
            multipartFile.transferTo(new File(fileSavePath));
            staticPath = StringUtils.replace(staticPath, "*", "");
            fileServerPath = staticPath + StringUtils.replace(fileSavePathNoCtx, File.separator, "/");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException("文件上传失败");
        }
        return fileServerPath;
    }

    /**
     * 删除
     *
     * @param files
     */
    public static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 文件大小转换
     *
     * @param size
     * @return
     */
    public static String getSize(int size) {
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }
}
