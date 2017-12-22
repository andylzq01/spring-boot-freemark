package com.wooyoo.learning.service.impl;

import com.wooyoo.learning.common.WeatherResponse;
import com.wooyoo.learning.service.WeatherService;
import com.wooyoo.learning.util.RedisCache;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl  implements WeatherService{
    @Override
    public WeatherResponse getWeekForecast() {
        RedisCache redisCache = new RedisCache("1");
        redisCache.getObject("");
        return null;
    }
}
