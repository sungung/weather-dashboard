package com.dpworld.weather.persist;

import javax.persistence.*;
import java.util.Date;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private String type;
    private String payload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;

        Weather weather = (Weather) o;

        return (id==null?weather.getId()==null:id==weather.getId())
                && timestamp.equals(weather.timestamp)
                && type.equals(weather.type);
    }

    @Override
    public int hashCode() {
        return this.id==null?(31*timestamp.hashCode()+type.hashCode()):id.hashCode();
    }
}
