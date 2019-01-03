package com.biubiu.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多数据源操作配置类
 * Created by Haibiao.Zhang on 2018/12/17.
 */
public class MultiDataSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiDataSourceConfig.class);

    private static Map<String, DruidDataSource> dataSources = new ConcurrentHashMap<>();

    public static DruidDataSource getOrCacheDataSource(String database, String url, String user, String password, String driver) {
        return Optional.ofNullable(dataSources.get(url)).orElseGet(() -> {
            DruidDataSource druidDataSource = addDataSource(url, user, password, driver);
            dataSources.put(database, druidDataSource);
            LOGGER.info("容器初始化缓存多数据源 {}", druidDataSource);
            return druidDataSource;
        });
    }

    public static Connection getConnection(String database) {
        DruidDataSource dataSource = dataSources.get(database);
        Connection connection = null;
        try {
            connection = dataSource != null ? dataSource.getConnection() : null;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void removeConnection(String database) {
        dataSources.remove(database);
    }

    public static Connection cacheConnection(String database, String url, String user, String password, String driver) {
        Connection connection = null;
        try {
            connection = getOrCacheDataSource(database, url, user, password, driver).getConnection();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    private static DruidDataSource addDataSource(String url, String user, String password, String driver) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setMaxActive(2);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(3000);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(1800);
        return druidDataSource;
    }

}
