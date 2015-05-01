package com.dpworld.weather.persist;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@Service
public class WeatherService {
    public Weather transform(byte[] buffer) {
        String data = new String(buffer);
        Weather weather = new Weather();
        weather.setTimestamp(new Date());
        weather.setType(data.split(",")[0]);
        weather.setPayload(new String(buffer));
        return weather;
    }
}
