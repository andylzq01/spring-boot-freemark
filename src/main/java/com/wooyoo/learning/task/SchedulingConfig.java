package com.wooyoo.learning.task;

import com.wooyoo.learning.util.HttpClientUtil;
import com.wooyoo.learning.util.RedisCache;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SchedulingConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestTemplate restTemplate;

      //  @Scheduled(cron = "0/30 * * * * ?") // 每20秒执行一次
   // @Scheduled(cron = "0 59 2 ? * FRI") // 每20秒执行一次
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> 开始同步天气预报信息>>>>>>>>>>>>>>>> ");
        String url = "http://wthrcdn.etouch.cn/weather_mini?city=深圳";
        String result = HttpClientUtil.requestByGetMethod(url);
        RedisCache redisCache = new RedisCache("1");
        Object obj = redisCache.getObject("fost");
        if (StringUtils.isNotBlank(result) && obj == null) {
            redisCache.putObject("fost", result);
        }else
        {

        }

        logger.info(">>>>>>>>>>>>> 天气预报报文信息:>>>>>>>>>>>>>>>> " + result);
    }

}