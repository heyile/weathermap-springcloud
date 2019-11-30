package com.huawei.weather.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.huawei.weather.sample.entity.FusionWeatherSummary;
import com.huawei.weather.sample.service.FusionweatherService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@EnableCircuitBreaker
@RestController
@RequestMapping("/fusionweather")
public class FusionWeatherController {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  private FusionweatherService fusionweatherdataService;

  @HystrixCommand(fallbackMethod = "helloFallBack")
  @RequestMapping("/show")
  public FusionWeatherSummary show(@RequestParam("city") String city,
      @RequestParam(value = "user", required = false) String user) {
    return fusionweatherdataService.showFusionWeather(city, user);
  }

  public FusionWeatherSummary helloFallBack() {
    System.out.println("fusion weather ==============");
    return new FusionWeatherSummary();
  }
}
