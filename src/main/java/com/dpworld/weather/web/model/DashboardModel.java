package com.dpworld.weather.web.model;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
public class DashboardModel {

    private WindObservation windObservation;
    private Forecast forecast;

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
}
