package com.dpworld.weather.web.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
public class WindObservation {
    Date observationTime;
    double windSpeed;
    Direction windDirection;
    double hourlyPeak;
    Direction peakDirection;
    double temperature;
    List<ObservationEvent> history = new LinkedList<ObservationEvent>();

    public static class ObservationEvent {
        Date timestamp;
        double windSpeed;
        Direction windDirection;

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public Direction getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(Direction windDirection) {
            this.windDirection = windDirection;
        }
    }

    public Date getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(Date observationTime) {
        this.observationTime = observationTime;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Direction getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Direction windDirection) {
        this.windDirection = windDirection;
    }

    public double getHourlyPeak() {
        return hourlyPeak;
    }

    public void setHourlyPeak(double hourlyPeak) {
        this.hourlyPeak = hourlyPeak;
    }

    public Direction getPeakDirection() {
        return peakDirection;
    }

    public void setPeakDirection(Direction peakDirection) {
        this.peakDirection = peakDirection;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public List<ObservationEvent> getHistory() {
        return history;
    }

    public void setHistory(List<ObservationEvent> history) {
        this.history = history;
    }

    public ObservationEvent newObservationEvent() {
        return new ObservationEvent();
    }
}
