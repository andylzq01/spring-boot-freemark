package com.wooyoo.learning.config.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Description: 数据源配置
 * All rights Reserved, Designed ByBeLLE
 * Copyright:   Copyright(C) 2014-2015
 * Company:     Wonhigh.
 * author:      laizeqi
 * Createdate:  ${date}${time}
 * <p>
 * Modification  History:
 * Date         Author             What
 * ------------------------------------------
 * ${date}     	laizeqi
 */

@Configuration
public class DataSourceConfiguration {
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Value("${mysql.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    /**
     * 写库 数据源
     * @return
     */
    @Bean("writeDataSource")
    @Primary //优先选择
    @ConfigurationProperties(prefix = "mysql.datasource.write")
    public DataSource writeDataSource(){
        logger.info("---------------------------writeDataSource init ----------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }


    /**
     * 读库 01 数据源
     * @return
     */
    @Bean("readDataSourceOne")
    @Primary //优先选择
    @ConfigurationProperties(prefix = "mysql.datasource.read01")
    public DataSource readDataSourceOne(){
        logger.info("---------------------------readDataSourceOne init ----------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * 读库 02 数据源,有多少个读库就得配置多少个
     * @return
     */
    @Bean("readDataSourceTwo")
    @Primary //优先选择
    @ConfigurationProperties(prefix = "mysql.datasource.read02")
    public DataSource readDataSourceTwo(){
        logger.info("---------------------------readDataSourceTwo init ----------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
}
