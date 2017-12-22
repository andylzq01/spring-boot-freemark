package com.wooyoo.learning.annotation;

import java.lang.annotation.*;

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
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) //保留运行时
@Inherited //自动被继承
@Documented
public @interface ReadDataSource {
}
