package com.wooyoo.learning.common;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Configuration
public class LogRecordAspect {

    private long beginTime = 0L;
    private Long endTime = 0L;
    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(* com.wooyoo.learning.web.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        logger.info("请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        JSONObject gson = new JSONObject();

        logger.info("请求结束，controller的返回值是 " + JSON.toJSON(result));
        return result;
    }

    //声明该方法是一个前置通知：在目标方法开始之前执行
    //@Before("execution(public int com.yl.spring.aop.impl.ArithmeticCalculatorImpl.add(int, int))")
    @Before("execution(* com.wooyoo.learning.web.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinpoint) {
        beginTime = System.currentTimeMillis();
        String methodName = joinpoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinpoint.getArgs());
        logger.info("The method " + methodName + " begins with " + args + ",开始执行时间：" + beginTime);
    }

    //后置通知：在目标方法执行后（无论是否发生异常），执行的通知
    //在后置通知中，还不能访问目标方法执行的结果
//    @After(value = "* com.wooyoo.learning.web.*Controller.*(..))")
//    public void afterMethod() {
//        afterMethod();
//    }

    //后置通知：在目标方法执行后（无论是否发生异常），执行的通知
    //在后置通知中，还不能访问目标方法执行的结果
    @After("execution(* com.wooyoo.learning.web.*Controller.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        endTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        logger.info("The method " + methodName + " end with " + args + ",接触时间：" + endTime);

        logger.info("一共消耗时间：" + (endTime - beginTime));
    }
}
