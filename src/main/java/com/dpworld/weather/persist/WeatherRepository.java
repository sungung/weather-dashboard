package com.dpworld.weather.persist;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */

public interface WeatherRepository extends CrudRepository<Weather, Long> {
    @Query(value = "select top 1 * from Weather where station = ?1 and type = ?2 order by timestamp desc", nativeQuery = true)
    Weather findTheLatestWeatherByType(String station, String type);

    @Query(value = "select top 1 * from Weather where station = ?1 and type = ?2 and timestamp >= ?3 order by observation desc", nativeQuery = true)
    Weather findPeakObservationByType(String station, String type, Date since);

    @Query(value = "select * from Weather where station = ?1 and type = ?2 and timestamp between ?3 and ?4 order by timestamp", nativeQuery = true)
    List<Weather> findObservationByPeriod(String station, String type, Date from, Date to);

    @Modifying
    @Query(value = "delete from Weather w where w.timestamp < ?1")
    @Transactional
    void deleteOldEntries(Date till);

}
