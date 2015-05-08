package com.dpworld.weather.persist;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */

public interface WeatherRepository extends CrudRepository<Weather, Long> {
    @Query(value = "select top 1 * from Weather where type = ?1 order by timestamp desc", nativeQuery = true)
    Weather findTheLatestWeatherByType(String type);

    @Query(value = "select top 1 * from Weather where type = ?1 and timestamp >= ?2 order by observation desc", nativeQuery = true)
    Weather findPeakObservationByType(String type, Date since);

    @Query(value = "select * from Weather where type = ?1 and timestamp between ?2 and ?3 order by timestamp", nativeQuery = true)
    List<Weather> findObservationByPeriod(String type, Date from, Date to);

}
