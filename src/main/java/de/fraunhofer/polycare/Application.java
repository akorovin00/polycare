package de.fraunhofer.polycare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by aw3s0 on 8/26/2017.
 * Main spring application
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@EntityScan("de.fraunhofer.polycare.models")
@EnableJpaRepositories("de.fraunhofer.polycare.persistence")
@ComponentScan(basePackages = "de.fraunhofer.polycare")
public class Application {
    public static void main(String[] args) {
        // runs application
        SpringApplication.run(Application.class, args);
    }
}
