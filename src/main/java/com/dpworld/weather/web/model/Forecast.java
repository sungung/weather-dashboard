package com.dpworld.weather.web.model;

import com.dpworld.weather.Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
public class Forecast implements Serializable {
    private Date timestamp;
    private String relativeTime;
    private String issued;
    private String city;
    private List<DailyForecast> dailyForecasts = new LinkedList<DailyForecast>();

    public static class DailyForecast implements Serializable {
        private Date date;
        private String header;
        private String forecast;
        private String summary;
        private String minTemp;
        private String maxTemp;
        private String icon;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getForecast() {
            return forecast;
        }

        public void setForecast(String forecast) {
            this.forecast = forecast;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(String minTemp) {
            this.minTemp = minTemp;
        }

        public String getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(String maxTemp) {
            this.maxTemp = maxTemp;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return "DailyForecast{" +
                    "date=" + date +
                    ", header='" + header + '\'' +
                    ", forecast='" + forecast + '\'' +
                    ", summary='" + summary + '\'' +
                    ", minTemp='" + minTemp + '\'' +
                    ", maxTemp='" + maxTemp + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        if (timestamp != null) {
            this.relativeTime = Utils.prettyPeriod(timestamp);
        }
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public DailyForecast newDailyForecast() {
        return new DailyForecast();
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "timestamp=" + timestamp +
                ", issued='" + issued + '\'' +
                ", city='" + city + '\'' +
                ", dailyForecasts=" + dailyForecasts +
                '}';
    }
}
