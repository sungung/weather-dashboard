package com.dpworld.weather.web;

import com.dpworld.weather.persist.Weather;
import com.dpworld.weather.persist.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@Controller
public class DashboardController {

    @Autowired
    WeatherRepository weatherRepository;

    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

    @RequestMapping("/api/wind")
    @ResponseBody
    public Map<String,Object> home() {
        Weather wind = weatherRepository.findTheLatestWeatherByType("0R1");
        Weather temp = weatherRepository.findTheLatestWeatherByType("0R5");
        List<Weather> list = (List<Weather>) weatherRepository.findAll();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, -1);
        Weather peak = weatherRepository.findPeakObservationByType("0R1", cal.getTime());
        return null;
    }

    @RequestMapping("/api/weather")
    @ResponseBody
    public Map<String,Object> weather() {
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello Weather");
        return model;
    }
}
