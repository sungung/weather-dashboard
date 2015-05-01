package com.dpworld.weather.persist;

import org.springframework.data.repository.CrudRepository;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */

public interface WeatherRepository extends CrudRepository<Weather, Long> {
}
