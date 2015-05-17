package com.dpworld.weather.web;

import com.dpworld.weather.messaging.WeatherService;
import com.dpworld.weather.persist.Station;
import com.dpworld.weather.persist.Weather;
import com.dpworld.weather.persist.WeatherRepository;
import com.dpworld.weather.web.model.DashboardModel;
import com.dpworld.weather.web.model.Forecast;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@Controller
public class DashboardController {

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    WeatherService weatherService;

    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

    @RequestMapping("/api/observation")
    @ResponseBody
    public List<DashboardModel> observation() {
        List<DashboardModel> list = new LinkedList<DashboardModel>();
        list.add(new DashboardModel(Station.ADMIN));
        list.get(0).setWindObservation(DashboardModel.toWindObservation(
                weatherRepository.findTheLatestWeatherByType(Station.ADMIN.name(), "0R1"),
                weatherRepository.findTheLatestWeatherByType(Station.ADMIN.name(), "0R5"),
                weatherRepository.findPeakObservationByType(Station.ADMIN.name(), "0R1", DateTime.now().minusHours(1).toDate()),
                (List<Weather>) weatherRepository.findObservationByPeriod(Station.ADMIN.name(), "0R1", DateTime.now().minusHours(8).toDate(), new Date())
        ));

        Forecast forecast = weatherService.fetchForecast();
        for (DashboardModel model : list) {
            model.setForecast(forecast);
        }

        return list;
    }

    @RequestMapping("/api/forecast")
    @ResponseBody
    public DashboardModel weather() {
        DashboardModel model = new DashboardModel();
        model.setForecast(weatherService.fetchForecast());
        return model;
    }

}
