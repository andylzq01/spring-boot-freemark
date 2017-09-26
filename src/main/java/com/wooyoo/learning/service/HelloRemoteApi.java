package com.wooyoo.learning.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name= "spring-boot-service")
public interface HelloRemoteApi {
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam("a")Integer a,@RequestParam("b")Integer b);


}