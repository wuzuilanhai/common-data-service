package com.biubiu;

import com.biubiu.config.MultiDataSourceConfig;
import com.biubiu.constant.DatabaseEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by Haibiao.Zhang on 2018/12/17.
 */
@SpringBootApplication
public class DataApplication implements CommandLineRunner {

    @Value("${jdbc.driverClassName}")
    private String driver1;

    @Value("${jdbc.url}")
    private String url1;

    @Value("${jdbc.username}")
    private String username1;

    @Value("${jdbc.password}")
    private String password1;

    @Value("${jdbc2.driverClassName}")
    private String driver2;

    @Value("${jdbc2.url}")
    private String url2;

    @Value("${jdbc2.username}")
    private String username2;

    @Value("${jdbc2.password}")
    private String password2;

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        //加载交易库
        MultiDataSourceConfig.getOrCacheDataSource(DatabaseEnum.TRADE.getDatabase(), url1, username1, password1, driver1);
        //加载用户库
        MultiDataSourceConfig.getOrCacheDataSource(DatabaseEnum.USER.getDatabase(), url2, username2, password2, driver2);
    }

}
