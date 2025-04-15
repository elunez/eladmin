/*
 *  Copyright 2019-2025 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.config.properties;

import lombok.Data;
import me.zhengjie.utils.ElConstant;
import me.zhengjie.utils.EncryptUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Objects;

/**
 * @author Zheng Jie
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /** 文件大小限制 */
    private Long maxSize;

    /** 头像大小限制 */
    private Long avatarMaxSize;

    private ElPath mac;

    private ElPath linux;

    private ElPath windows;

    public ElPath getPath(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith(ElConstant.WIN)) {
            return windows;
        } else if(os.toLowerCase().startsWith(ElConstant.MAC)){
            return mac;
        }
        return linux;
    }

    @Data
    public static class ElPath{

        private String path;

        private String avatar;
    }

    // region 支持FTP配置
    private FtpConfig ftp;

    @Data
    public static class FtpConfig {
        private String host;
        private int port;
        private String username;
        private String password;
        private String mainPath;
        private int connectTimeout = 5000;    // ms
        private int dataTimeout = 30000;    // ms
        // 连接池相关配置
        private int minIdle = 1;
        private int maxIdle = 3;
        private int maxTotal = 5;
        private long maxWait = 5000;    // ms

        public String getPassword() {
            String password = this.password;
            try {
                password = EncryptUtils.desDecrypt(this.password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return password;
        }
    }

    /**
     * 获取刨除服务器文件主目录外的相对路径（用于FTP的相对路径）
     *
     * @param file
     * @return
     */
    public String getRelativePath(File file) {
        String mainPath = getPath().getPath();
        String ftpPath = getFtp().getMainPath();
        if (Objects.nonNull(file)) {
            if (file.getPath().contains(mainPath)) {
                int len = file.getPath().length() - file.getName().length();
                String result = file.getPath().substring(mainPath.length(), len);
                // 处理分割符
                return dealPath(result);
            } else if (file.getPath().contains(ftpPath)) {
                int len = file.getPath().length() - file.getName().length();
                String result = file.getPath().substring(ftpPath.length(), len);
                // 处理分割符
                return dealPath(result);
            }
        }
        return file.getPath();
    }

    /**
     * 处理文件路径分隔符
     *
     * @param path
     * @return
     */
    private String dealPath(String path) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith(ElConstant.WIN) && path.contains("\\")) {
            return path.replace("\\", "/");
        } else if (os.toLowerCase().startsWith(ElConstant.MAC)) {
            return path;
        }
        return path;
    }

    // endregion
}
