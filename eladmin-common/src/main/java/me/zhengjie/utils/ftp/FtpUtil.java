package me.zhengjie.utils.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author pcshao.cn
 * @date 20/01/2024
 */
@Slf4j
public class FtpUtil {

    /**
     * FTP文件上传
     *
     * @param ftpClient
     * @param localFilePath
     * @param remoteDirectory
     * @return
     */
    public static boolean uploadFile(FTPClient ftpClient, String localFilePath, String remoteDirectory) {
        boolean done = false;
        try {
            // 检查远程目录是否存在
            checkRemoteDir(ftpClient, remoteDirectory);

            // 设置远程目录的字符编码为UTF-8
            ftpClient.changeWorkingDirectory(new String(remoteDirectory.getBytes(StandardCharsets.UTF_8)));

            File localFile = new File(localFilePath);

            FileInputStream inputStream = new FileInputStream(localFile);

            String remoteFilePath = remoteDirectory + localFile.getName();

            done = ftpClient.storeFile(remoteFilePath, inputStream);

            if (done) {
                log.info("FtpUtil uploadFile successfully.");
            } else {
                log.error("FtpUtil uploadFile failed. reply: {} remotePath: {} obj: {}", ftpClient.getReplyString(), remoteFilePath, ftpClient);
            }
            inputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return done;
    }

    /**
     * FTP文件上传
     *
     * @param server
     * @param port
     * @param username
     * @param password
     * @param localFilePath
     * @param remoteDirectory
     */
    public static boolean uploadFile(String server, int port, String username, String password, String localFilePath, String remoteDirectory) {
        boolean done = false;
        FTPClient ftpClient = new FTPClient();
        try {
            // 设置控制连接的字符编码为UTF-8
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(server, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 检查远程目录是否存在
            checkRemoteDir(ftpClient, remoteDirectory);

            // 设置远程目录的字符编码为UTF-8
            ftpClient.changeWorkingDirectory(new String(remoteDirectory.getBytes(StandardCharsets.UTF_8)));

            File localFile = new File(localFilePath);

            FileInputStream inputStream = new FileInputStream(localFile);

            String remoteFilePath = remoteDirectory + localFile.getName();

            done = ftpClient.storeFile(remoteFilePath, inputStream);

            if (done) {
                log.info("FtpUtil uploadFile successfully.");
            } else {
                log.info("FtpUtil uploadFile failed. reply: {} remotePath: {}", ftpClient.getReplyString(), remoteFilePath);
            }
            inputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return done;
    }

    /**
     * 检查远程目录，如果不存在则创建
     *
     * @param ftpClient
     * @param remoteDirectory
     * @throws IOException
     */
    private static void checkRemoteDir(FTPClient ftpClient, String remoteDirectory) throws IOException {
        FTPFile[] ftpFiles = ftpClient.mlistDir(remoteDirectory);
        log.info("FtpUtil mlistDir {} reply: {}", remoteDirectory, ftpClient.getReplyString());
        if (ftpFiles.length <= 0) {
            ftpClient.makeDirectory(remoteDirectory);
            log.info("FtpUtil makeDirectory {} reply: {}", remoteDirectory, ftpClient.getReplyString());
        }
    }

    /**
     * FTP文件下载
     *
     * @param ftpClient
     * @param remoteFilePath
     * @param localDirectory
     * @return
     */
    public static File downloadFile(FTPClient ftpClient, String remoteFilePath, String localDirectory) {
        File localFile = null;
        try {
            // 设置远程目录的字符编码为UTF-8
            String remoteDirectory = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/") + 1);
            ftpClient.changeWorkingDirectory(new String(remoteDirectory.getBytes(StandardCharsets.UTF_8)));

            String remoteFileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1);
            localFile = new File(localDirectory + remoteFileName);

            OutputStream outputStream = new FileOutputStream(localFile);

            boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);

            if (success) {
                log.info("FtpUtil downloaded successfully.");
            } else {
                log.info("FtpUtil download failed. reply: {} remotePath: {}", ftpClient.getReplyString(), remoteFilePath);
                return null;
            }

            outputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return localFile;
    }

    /**
     * FTP文件下载
     *
     * @param server
     * @param port
     * @param username
     * @param password
     * @param remoteFilePath
     * @param localDirectory
     * @return
     */
    public static File downloadFile(String server, int port, String username, String password, String remoteFilePath, String localDirectory) {
        File localFile = null;
        FTPClient ftpClient = new FTPClient();
        try {
            // 设置控制连接的字符编码为UTF-8
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(server, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 设置远程目录的字符编码为UTF-8
            String remoteDirectory = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/") + 1);
            ftpClient.changeWorkingDirectory(new String(remoteDirectory.getBytes(StandardCharsets.UTF_8)));

            String remoteFileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1);
            localFile = new File(localDirectory + remoteFileName);

            OutputStream outputStream = new FileOutputStream(localFile);

            boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);

            if (success) {
                log.info("FtpUtil downloaded successfully.");
            } else {
                log.info("FtpUtil download failed. reply: {} remotePath: {}", ftpClient.getReplyString(), remoteFilePath);
                return null;
            }

            outputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return localFile;
    }

    /**
     * FTP文件删除
     *
     * @param ftpClient
     * @param remoteFilePath
     * @return
     */
    public static boolean deleteFile(FTPClient ftpClient, String remoteFilePath) {
        boolean success = false;
        try {
            // 设置远程目录的字符编码为UTF-8
            String remoteDirectory = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/") + 1);
            ftpClient.changeWorkingDirectory(new String(remoteDirectory.getBytes(StandardCharsets.UTF_8)));

            String remoteFileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1);

            success = ftpClient.deleteFile(remoteFileName);

            if (success) {
                log.info("FtpUtil delete successfully.");
            } else {
                log.info("FtpUtil delete failed. reply: {} remotePath: {}", ftpClient.getReplyString(), remoteFilePath);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return success;
    }

    /**
     * FTP文件删除
     *
     * @param server
     * @param port
     * @param username
     * @param password
     * @param remoteFilePath
     * @return
     */
    public static boolean deleteFile(String server, int port, String username, String password, String remoteFilePath) {
        boolean success = false;
        FTPClient ftpClient = new FTPClient();
        try {
            // 设置控制连接的字符编码为UTF-8
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(server, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 设置远程目录的字符编码为UTF-8
            String remoteDirectory = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/") + 1);
            ftpClient.changeWorkingDirectory(new String(remoteDirectory.getBytes(StandardCharsets.UTF_8)));

            String remoteFileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1);

            success = ftpClient.deleteFile(remoteFileName);

            if (success) {
                log.info("FtpUtil delete successfully.");
            } else {
                log.info("FtpUtil delete failed. reply: {} remotePath: {}", ftpClient.getReplyString(), remoteFilePath);
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return success;
    }
}
