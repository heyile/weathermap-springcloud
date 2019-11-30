package com.huawei.weather.sample.controller;

import com.huawei.weather.sample.entity.objective.CurrentWeatherSummary;
import com.huawei.weather.sample.util.CacheUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableCircuitBreaker
@RestController
public class WeatherImpl {

    @Autowired
    CacheUtil cacheUtil;

    @HystrixCommand(fallbackMethod = "serviceFallback")
    @RequestMapping(value = "/show")
    public CurrentWeatherSummary showCurrentWeather(@RequestParam("city") String city) {
        return cacheUtil.getCurrentWeatherSummary(StringUtils.isNotBlank(city) ? city : "shenzhen,cn");
    }

    public CurrentWeatherSummary serviceFallback() { // fallback方法
        System.out.println("weather --------------- callback");
        return new CurrentWeatherSummary();
    }

}
