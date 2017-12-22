package com.wooyoo.learning.config.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:  本地线程，数据源上下文
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
public class DataSourceContextHolder {
    private static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public ThreadLocal<String> getLocal(){
        return threadLocal;
    }

    /**
     * 切换读库
     */
    public static void  setRead() {
        threadLocal.set(DataSourceType.read.getType());
        log.info("数据库切到读库");

    }

    /**
     * 切换写库
     */
    public static void setWrite(){
        threadLocal.set(DataSourceType.write.getType());
        log.info("数据库切到写库");
    }

    public static String readOrWrite(){
        return threadLocal.get();
    }

    /**
     * 清除threadLocal
     */
    public static void clear(){
        threadLocal.remove();;
    }
}
