package com.dpworld.weather.web.model;

import com.dpworld.weather.Utils;
import com.dpworld.weather.persist.Station;
import com.dpworld.weather.persist.Weather;

import java.util.List;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
public class DashboardModel {

    private Station station;
    private WindObservation windObservation;
    private Forecast forecast;

    public DashboardModel() {}

    public DashboardModel(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public WindObservation getWindObservation() {
        return windObservation;
    }

    public void setWindObservation(WindObservation windObservation) {
        this.windObservation = windObservation;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public static WindObservation toWindObservation(Weather wind,
                                                    Weather temperature,
                                                    Weather peakWind,
                                                    List<Weather> windHistory) {

        WindObservation observation = new WindObservation();
        observation.setObservationTime(wind.getTimestamp());
        observation.setWindSpeed(wind.getObservation());
        observation.setWindDirection(Direction.getCardinalDirection(wind.getDirection()));
        observation.setTemperature(temperature.getObservation());
        observation.setHourlyPeak(peakWind.getObservation());
        observation.setPeakDirection(Direction.getCardinalDirection(peakWind.getDirection()));
        observation.getChartFeed().getSeries().add("Speed");
        // show only one in fifty entry

        int threshold = 48;
        int size = windHistory.size();

        for (int i = 0; i < windHistory.size(); i++) {
            if (i%300 == 0) {
                //observation.getHistory().add(toObservationEvent(windHistory.get(i)));
                WindObservation.ChartData cd = new WindObservation.ChartData();
                cd.setX(Utils.shortenTime(windHistory.get(i).getTimestamp()));
                cd.getY().add(windHistory.get(i).getObservation());
                observation.getChartFeed().getData().add(cd);
            }
        }
        return observation;
    }

    private static WindObservation.ObservationEvent toObservationEvent(Weather weather) {
        WindObservation.ObservationEvent observationEvent = new WindObservation.ObservationEvent();
        observationEvent.setTimestamp(weather.getTimestamp());
        observationEvent.setWindDirection(Direction.getCardinalDirection(weather.getDirection()));
        observationEvent.setWindSpeed(weather.getObservation());
        return observationEvent;
    }
}
