package com.dpworld.weather.messaging;

import com.dpworld.weather.persist.Weather;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@Service
public class WeatherService {

    public Weather transform(byte[] buffer) {
        String observation;
        String data = new String(buffer);
        Weather weather = new Weather();
        weather.setTimestamp(new Date());
        weather.setType(data.split(",")[0]);
        weather.setPayload(data);
        if ("0R1".equals(weather.getType())) {
            observation = (data.split(",")[5]).split("=")[1];
            weather.setObservation(Double.valueOf(observation.substring(0,observation.length()-1)));
            observation = (data.split(",")[2]).split("=")[1];
            weather.setDirection(Integer.valueOf(observation.substring(0,observation.length()-1)));
        } else if ("0R5".equals(weather.getType())) {
            observation = (data.split(",")[2]).split("=")[1];
            weather.setObservation(Double.valueOf(observation.substring(0,observation.length()-1)));
        }
        return weather;
    }

}
