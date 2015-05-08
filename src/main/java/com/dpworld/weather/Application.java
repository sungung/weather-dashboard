package com.dpworld.weather;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@SpringBootApplication
@ImportResource({"integration-windsensor-context.xml","integration-bom-context.xml"})
public class Application implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
