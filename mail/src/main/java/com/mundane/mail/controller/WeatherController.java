package com.mundane.mail.controller;

import com.mundane.mail.service.ScheduleService;
import com.mundane.mail.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail/weather")
public class WeatherController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/send")
    public String sendWeatherReport() {
        return "hello";
    }
}
