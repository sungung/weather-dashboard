package com.dpworld.weather;

import com.dpworld.weather.persist.WeatherRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@ImportResource({"integration-windsensor-context.xml","integration-bom-context.xml"})
public class Application implements CommandLineRunner {

    @Autowired
    WeatherRepository weatherRepository;

    @Override
    public void run(String... args) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManager() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    /***
     * Clean up old entry to prevent memory issue
     */
    @Scheduled(fixedRate=1800000)
    public void cleanupSensorData() {
        weatherRepository.deleteOldEntries(new DateTime().minusHours(8).toDate());
    }


}
