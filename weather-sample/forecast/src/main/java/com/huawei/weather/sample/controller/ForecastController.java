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

    @RequestMapping(value = "/show")
    @HystrixCommand(fallbackMethod = "serviceFallback")
    public ForecastSummary getForecast(@RequestParam("city") String city) {
        return cacheUtil.getForecastWeatherSummary(StringUtils.isNotBlank(city) ? city : "ShenZhen,CN");
    }

  public ForecastSummary serviceFallback(String city) { // fallback方法
    return new ForecastSummary();
  }
}
