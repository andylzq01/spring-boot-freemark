package com.wooyoo.learning.config.db;

import com.github.pagehelper.PageHelper;
import com.wooyoo.learning.util.SpringContextUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
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
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@MapperScan(basePackages="com.wooyoo.learning.dao")
public class MybatisConfiguration {
    private static Logger log = LoggerFactory.getLogger(MybatisConfiguration.class);

    @Value("${mysql.datasource.readSize}")
    private String readDataSourceSize;

    //XxxMapper.xml文件所在路径
    @Value("${mysql.datasource.mapperLocations}")
    private String mapperLocations;

    //  加载全局的配置文件
    @Value("${mysql.datasource.configLocation}")
    private String configLocation;

    @Autowired
    @Qualifier("writeDataSource") //注释指定注入 Bean 的名称，这样歧义就消除了
    private DataSource writeDataSource;
    @Autowired
    @Qualifier("readDataSourceOne")
    private DataSource readDataSourceOne;
    @Autowired
    @Qualifier("readDataSourceTwo")
    private DataSource readDataSourceTwo;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());
        //读取映射实体
        sqlSessionFactoryBean.setTypeAliasesPackage("com.wooyoo.learning.dao.domain");

        //读取*.xml mybatis映射文件的路径
        Resource[] resources =  new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        sqlSessionFactoryBean.setMapperLocations(resources);

        //设置mybatis-config.xml路径
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

        //添加分页插件、打印sql插件
        Interceptor[] plugins = new Interceptor[]{pageHelper(),new SqlPrintInterceptor()};
        sqlSessionFactoryBean.setPlugins(plugins);

        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 把所有数据源都放在路由上
     *
     * @return
     */
    @Bean(name = "roundRobinDataSouceProxy")
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一至，
        //否则切换数据源时找不到正确的数据源
        targetDataSources.put(DataSourceType.write.getType(), writeDataSource);
        targetDataSources.put(DataSourceType.read.getType() + "1", readDataSourceOne);
        targetDataSources.put(DataSourceType.read.getType() + "2", readDataSourceTwo);
        //读库个数
        final int readSize = Integer.parseInt(readDataSourceSize);
        //路由寻找对应的数据源
        AbstractRoutingDataSource abstractRoutingDataSource = new AbstractRoutingDataSource() {
            private AtomicInteger count = new AtomicInteger(0);

            @Override
            protected Object determineCurrentLookupKey() {
                String typeKey = DataSourceContextHolder.readOrWrite();
                if (typeKey == null) {
                    throw new NullPointerException("数据库路由时，决定使用哪个数据库源类型不能为null...");
                }

                //使用写库
                if (DataSourceType.write.getType().equalsIgnoreCase(typeKey)) {
                    log.info("使用数据库write.............");
                    return DataSourceType.write.getType();
                }

                //读库， 简单负载均衡
                int number = count.getAndAdd(1);
                int lookupKey = number % readSize;
                log.info("使用数据库read-"+(lookupKey+1));
                return DataSourceType.read.getType()+(lookupKey+1);
            }
        };
        abstractRoutingDataSource.setDefaultTargetDataSource(writeDataSource); //默认写库
        abstractRoutingDataSource.setTargetDataSources(targetDataSources);
        return abstractRoutingDataSource;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    //事务管理
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager((DataSource) SpringContextUtil.getBean("roundRobinDataSouceProxy"));
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("returnPageInfo", "check");
        p.setProperty("params", "count=countSql");
        pageHelper.setProperties(p);
        return pageHelper;
    }

}
