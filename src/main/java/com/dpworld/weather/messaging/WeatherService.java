package com.dpworld.weather.messaging;

import com.dpworld.weather.persist.Weather;
import com.dpworld.weather.persist.Station;
import com.dpworld.weather.web.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@Service
public class WeatherService {

    @Autowired
    @Qualifier("bomResponseChannel")
    PollableChannel bomResponseChannel;

    @Autowired
    @Qualifier("bomRequestChannel")
    MessageChannel bomRequestChannel;

    public Weather transform(byte[] buffer, Station station) {
        String observation;
        String data = new String(buffer);
        Weather weather = new Weather();
        weather.setStation(station);
        weather.setTimestamp(new Date());
        weather.setType(data.split(",")[0]);
        weather.setPayload(data);
        if ("0R1".equals(weather.getType())) {
            observation = (data.split(",")[5]).split("=")[1];
            weather.setObservation(Double.valueOf(observation.substring(0,observation.length()-1)));
            observation = (data.split(",")[2]).split("=")[1];
            weather.setDirection(Integer.valueOf(observation.substring(0,observation.length()-1)));
        } else if ("0R5".equals(weather.getType())) {
            observation = (data.split(",")[1]).split("=")[1];
            weather.setObservation(Double.valueOf(observation.substring(0,observation.length()-1)));
        }
        return weather;
    }

    @Cacheable("forecast")
    public Forecast fetchForecast() {
        bomRequestChannel.send(MessageBuilder.withPayload("").build());
        Message<Forecast> message = (Message<Forecast>) bomResponseChannel.receive();
        return message.getPayload();
    }

}
