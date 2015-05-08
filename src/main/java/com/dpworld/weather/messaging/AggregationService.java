package com.dpworld.weather.messaging;

import com.dpworld.weather.persist.Weather;
import com.dpworld.weather.persist.WeatherRepository;
import com.dpworld.weather.web.model.DashboardModel;
import com.dpworld.weather.web.model.Direction;
import com.dpworld.weather.web.model.Forecast;
import com.dpworld.weather.web.model.WindObservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@Service
public class AggregationService {

    @Autowired
    WeatherRepository weatherRepository;

    public DashboardModel aggregateObservation() {

        DashboardModel model = new DashboardModel();
        model.setForecast(new Forecast());
        model.setWindObservation(new WindObservation());

        Weather wind = weatherRepository.findTheLatestWeatherByType("0R1");
        Weather temp = weatherRepository.findTheLatestWeatherByType("0R5");
        List<Weather> list = (List<Weather>) weatherRepository.findAll();

        Map<String,String> weather = parseWeatherPayload(wind.getPayload());

        model.getWindObservation().setWindSpeed(Double.valueOf(weather.get("Sm")));
        model.getWindObservation().setWindDirection(Direction.getCardinalDirection(Double.valueOf(weather.get("Dm"))));

        return model;
    }

    private Map<String, String> parseWeatherPayload(String payload) {
        Map<String, String> weather = new HashMap<String, String>();
        String[] list = payload.split(",");
        for (String observe : list) {
            if (observe.contains("=")) {
                String[] items = observe.split("=");
                weather.put(items[0], items[1].substring(0,items[1].length()-1));
            }
        }
        return weather;
    }
}
