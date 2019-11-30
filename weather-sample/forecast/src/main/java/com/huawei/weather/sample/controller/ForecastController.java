package com.huawei.weather.sample.controller;

import com.huawei.weather.sample.entity.objective.ForecastSummary;
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
public class ForecastController {

    @Autowired
    CacheUtil cacheUtil;

    @HystrixCommand(fallbackMethod = "serviceFallback")
    @RequestMapping(value = "/show")
    public ForecastSummary getForecast(@RequestParam("city") String city) {
        return cacheUtil.getForecastWeatherSummary(StringUtils.isNotBlank(city) ? city : "ShenZhen,CN");
    }

    public String serviceFallback() { // fallback方法
        return "error";
    }
}
