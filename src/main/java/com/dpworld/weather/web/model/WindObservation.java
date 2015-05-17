package com.dpworld.weather.web.model;

import com.dpworld.weather.Utils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
public class WindObservation {
    Date observationTime;
    String relativeObsTime;
    double windSpeed;
    Direction windDirection;
    double hourlyPeak;
    Direction peakDirection;
    double temperature;
    List<ObservationEvent> history = new LinkedList<ObservationEvent>();
    ChartFeed chartFeed = new ChartFeed();

    public static class ChartFeed {
        List<String> series = new LinkedList<String>();
        List<ChartData> data = new LinkedList<ChartData>();

        public List<String> getSeries() {
            return series;
        }

        public void setSeries(List<String> series) {
            this.series = series;
        }

        public List<ChartData> getData() {
            return data;
        }

        public void setData(List<ChartData> data) {
            this.data = data;
        }
    }

    public static class ChartData {
        String x;
        List<Object> y = new LinkedList<Object>();

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public List<Object> getY() {
            return y;
        }

        public void setY(List<Object> y) {
            this.y = y;
        }
    }

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
        if (observationTime != null) {
            this.relativeObsTime = Utils.prettyPeriod(observationTime);
        }
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

    public String getRelativeObsTime() {
        return relativeObsTime;
    }

    public void setRelativeObsTime(String relativeObsTime) {
        this.relativeObsTime = relativeObsTime;
    }

    public ChartFeed getChartFeed() {
        return chartFeed;
    }

    public void setChartFeed(ChartFeed chartFeed) {
        this.chartFeed = chartFeed;
    }
}
