package me.zhengjie.utils.ftp;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.config.properties.FileProperties;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.function.Function;

/**
 * FTP连接池管理器
 *
 * @author pcshao.cn
 * @date 13/04/2024
 */
@Slf4j
@Service
public class FtpConnectionPoolManager {

    private volatile static GenericObjectPool<FTPClient> pool = null;

    @Resource
    private FileProperties fileProperties;

    /**
     * 初始化连接池
     */
    public void initPool() {
        if (Objects.nonNull(pool))
            return;
        else {
            // 懒加载
            synchronized (this) {
                if (Objects.nonNull(pool))
                    return;
            }
        }
        FileProperties.FtpConfig ftp = fileProperties.getFtp();
        GenericObjectPoolConfig<FTPClient> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(ftp.getMaxTotal()); // 设置连接池最大连接数
        poolConfig.setMaxIdle(ftp.getMaxIdle()); // 设置连接池最大空闲连接数
        poolConfig.setMinIdle(ftp.getMinIdle()); // 设置连接池最小空闲连接数
        poolConfig.setMaxWaitMillis(ftp.getMaxWait()); // 设置连接池最大等待时间，单位为毫秒
        poolConfig.setTestOnBorrow(true);   // 设置获取连接前进行检查

        pool = new GenericObjectPool<>(new BasePooledObjectFactory<FTPClient>() {
            @Override
            public FTPClient create() throws Exception {
                String server = ftp.getHost();
                int port = ftp.getPort();
                String username = ftp.getUsername();
                String password = ftp.getPassword();
                FTPClient ftpClient = new FTPClient();
                // 设置控制连接的字符编码为UTF-8
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.connect(server, port);
                ftpClient.login(username, password);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.setConnectTimeout(ftp.getConnectTimeout()); // 连接超时时间为5秒
                ftpClient.setDataTimeout(ftp.getDataTimeout()); // 数据传输超时时间为30秒
                return ftpClient;
            }

            @Override
            public void destroyObject(PooledObject<FTPClient> p) throws Exception {
                FTPClient ftpClient = p.getObject();
                ftpClient.logout();
                ftpClient.disconnect();
            }

            @Override
            public boolean validateObject(PooledObject<FTPClient> p) {
                FTPClient ftpClient = p.getObject();
                try {
                    log.warn("FTP连接池管理 检测FTP连接状态 reply:{} status:{} obj:{}", ftpClient.getReplyString(), ftpClient.getStatus(), ftpClient);
                    return ftpClient.isConnected() && ftpClient.sendNoOp();
                } catch (Exception e) {
                    log.warn("FTP连接池管理 检测FTP连接失败 {}", e.getMessage());
                    if (log.isDebugEnabled())
                        log.debug(e.getMessage(), e);
                    return false;
                }
            }

            @Override
            public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
                DefaultPooledObject defaultPooledObject = new DefaultPooledObject(ftpClient);
                return defaultPooledObject;
            }
        }, poolConfig);
    }

    /**
     * 执行方法，包含获取和归还连接
     *
     * @param function
     * @param <R>
     * @return
     * @throws Exception
     */
    public <R> R execute(Function<FTPClient, R> function) throws Exception {
        FTPClient ftpClient = null;
        try {
            ftpClient = borrowFTPClient();
            return function.apply(ftpClient);
        } finally {
            returnFTPClient(ftpClient);
        }
    }

    /**
     * 获取一个连接
     *
     * @return
     * @throws Exception
     */
    public FTPClient borrowFTPClient() throws Exception {
        initPool();
        return pool.borrowObject();
    }

    /**
     * 归还连接
     *
     * @param ftpClient
     */
    public void returnFTPClient(FTPClient ftpClient) {
        initPool();
        pool.returnObject(ftpClient);
    }

    /**
     * 关闭连接池
     */
    public void close() {
        if (Objects.isNull(pool))
            return;
        pool.close();
    }
}
